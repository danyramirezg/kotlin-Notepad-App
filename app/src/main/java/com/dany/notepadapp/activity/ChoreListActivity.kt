package com.dany.notepadapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dany.notepadapp.R
import com.dany.notepadapp.data.ChoreListAdapter
import com.dany.notepadapp.data.ChoresDatabaseHandler
import com.dany.notepadapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {

    private var choreList: ArrayList<Chore>? = null
    private var adapter: ChoreListAdapter? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: ChoresDatabaseHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler = ChoresDatabaseHandler(this)
        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        adapter = ChoreListAdapter(choreListItems!!, this)

        layoutManager = LinearLayoutManager(this)

        // Setup list = RecyclerView
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        // Load the tasks
        choreList = dbHandler!!.readChores()
        choreList?.reverse()

        for (chor in choreList!!.iterator()) {

            val chore = Chore()
            chore.choreName = "Task: ${chor.choreName}"
            chore.description = "Description: ${chor.description}"
            chore.assignedBy = "Assigned by: ${chor.assignedBy}"
            chore.id = chor.id
            chor.showHumanDate(chor.timeAssigned!!)

            choreListItems?.add(chore)


            Log.d("=====>ID: ", chor.id.toString())
            chor.choreName?.let { Log.d("=====>Task name: ", it) }
            Log.d("=====>Description: ", chor.description!!)
            Log.d("=====>Assigned by: ", chor.assignedBy!!)
            Log.d("=====>Date: ", chore.showHumanDate(chor.timeAssigned!!))

        }

        adapter?.notifyDataSetChanged()
    }

    // Override functions for the menu (check the directory out in res->menu)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_menu_button) {
            Log.d("Click", "Item clicked")
            Toast.makeText(this, "Item Clicked", Toast.LENGTH_LONG).show()
            createPopupDialog()

        }
        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog() {

        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.poptitleId
        var description = view.popDescriptionId
        var assignedBy = view.popAssignedId
        var saveButton = view.popSaveTask

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder?.create()
        dialog?.show()

        saveButton.setOnClickListener {
            var name = choreName.text.toString().trim()
            var des = description.text.toString().trim()
            var aBy = assignedBy.text.toString().trim()

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(des) && !TextUtils.isEmpty(aBy)) {

                var chore = Chore()
                chore.choreName = name
                chore.description = des
                chore.assignedBy = aBy

                dbHandler?.createChore(chore)

                dialog?.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Testing", Toast.LENGTH_LONG).show()
            }
        }
    }
}
