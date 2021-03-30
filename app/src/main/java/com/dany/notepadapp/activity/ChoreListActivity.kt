package com.dany.notepadapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dany.notepadapp.R
import com.dany.notepadapp.data.ChoreListAdapter
import com.dany.notepadapp.data.ChoresDatabaseHandler
import com.dany.notepadapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*

class ChoreListActivity : AppCompatActivity() {

    private var choreList: ArrayList<Chore>? = null
    private var adapter: ChoreListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler = ChoresDatabaseHandler(this)

        choreList = ArrayList<Chore>()
        adapter = ChoreListAdapter(choreList!!, this)
        layoutManager = LinearLayoutManager(this)

        // Setup list = RecyclerView
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        // Load the tasks
        choreList = dbHandler!!.readChores()

        for (chor in choreList!!.iterator()){

            chor.choreName?.let { Log.d("Testing output", it) }
        }
    }
}