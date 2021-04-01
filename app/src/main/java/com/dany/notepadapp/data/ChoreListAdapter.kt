package com.dany.notepadapp.data

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dany.notepadapp.R
import com.dany.notepadapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListAdapter(private val list: ArrayList<Chore>,
                       private val context: Context) : RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        // Create the view from the list_row.xml
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view, context, list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    // create inner class to invoke some functions
    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Chore>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        // To get the views from the file list_row.xml
        var myContext = context
        var myList = list


        var choreName = itemView.findViewById(R.id.listChoreName) as TextView
        var description = itemView.findViewById(R.id.listDescription) as TextView
        var assignedBy = itemView.findViewById(R.id.listAssignedBy) as TextView
        var assignedDate = itemView.findViewById(R.id.listDate) as TextView

        var deleteButton = itemView.findViewById(R.id.listDeleteButton) as Button
        var editButton = itemView.findViewById(R.id.listEditButton) as Button

        fun bindViews(chore: Chore) {

            choreName.text = chore.choreName
            description.text = chore.description
            assignedBy.text = chore.assignedBy
            assignedDate.text = chore.showHumanDate(System.currentTimeMillis())

            deleteButton.setOnClickListener(this)
            editButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var myPosition: Int = adapterPosition
            var chore = myList[myPosition]

            Log.d("IDD: ", chore.toString())


            when (v?.id) {
                // Delete chore
                deleteButton.id -> {
                    deleteChore(chore.id!!)
                    myList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }

                editButton.id -> {
                    editChore(chore)
                }
            }
        }

        fun deleteChore(id: Int) {
            var db: ChoresDatabaseHandler = ChoresDatabaseHandler(myContext)
            db.deleteChore(id)
        }

        fun editChore(chore: Chore) {

            // To popup:
            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog
            var dbHandler: ChoresDatabaseHandler = ChoresDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)
            var choreName = view.poptitleId
            var description = view.popDescriptionId
            var assignedBy = view.popAssignedId
            var saveButton = view.popSaveTask

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder.create()
            dialog.show()

            saveButton.setOnClickListener {
                var name = choreName.text.toString().trim()
                var des = description.text.toString().trim()
                var aBy = assignedBy.text.toString().trim()

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(des) && !TextUtils.isEmpty(aBy)) {
                    chore.choreName = name
                    chore.description = des
                    chore.assignedBy = aBy

                    dbHandler.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore)

                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "Please fill in the empty fields", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
