package com.dany.notepadapp.data

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dany.notepadapp.R
import com.dany.notepadapp.model.Chore

class ChoreListAdapter(private val list: ArrayList<Chore>,
                       private val context: Context) : RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var choreName = itemView.findViewById(R.id.listChoreName) as TextView
        var description = itemView.findViewById(R.id.listDescription) as TextView
        var assignedBy = itemView.findViewById(R.id.listAssignedBy) as TextView
        var assignedDate = itemView.findViewById(R.id.listDate) as TextView

        fun bindViews(chore: Chore){
            choreName.text = chore.choreName
            description.text = chore.description
            assignedBy.text = chore.assignedBy
            assignedDate.text = chore.timeAssigned.toString()
        }

    }



}
