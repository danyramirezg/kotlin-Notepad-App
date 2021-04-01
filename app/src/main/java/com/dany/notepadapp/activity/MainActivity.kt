package com.dany.notepadapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.dany.notepadapp.R
import com.dany.notepadapp.data.ChoresDatabaseHandler
import com.dany.notepadapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)

        checkDB()

        saveTask.setOnClickListener {

            // Progress bar: This is visible on click "save task"

            progressBarId.visibility = View.VISIBLE

            // If the fields aren't empty, save them in the database

            if (!TextUtils.isEmpty(titleId.text.toString())
                    && !TextUtils.isEmpty(popAssignedId.text.toString())
                    && !TextUtils.isEmpty(descriptionId.text.toString())) {

                // Save to database:

                var chore = Chore()
                chore.choreName = titleId.text.toString()
                chore.description = descriptionId.text.toString()
                chore.assignedBy = popAssignedId.text.toString()

                // To debug, to see if data is inserted
                println("======> chore name: ${chore.choreName}, Description: ${chore.description}, " +
                        "assigned by: ${chore.assignedBy}")

                saveToDataBase(chore)


                // When loading a new activity, the progress bar is Gone
                progressBarId.visibility = View.GONE

                // Open a new activity:
                startActivity(Intent(this, ChoreListActivity::class.java))
            } else {
                Toast.makeText(this, "Please complete the empty fields", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun saveToDataBase(chore: Chore) {
        dbHandler?.createChore(chore)
    }

    // Checks if the database has at least one table:

    fun checkDB() {
        if (dbHandler!!.getChoresCount() > 0) {
            startActivity((Intent(this, ChoreListActivity::class.java)))

        }
    }
}

//        var chore = Chore()
//        chore.choreName = "Things to do"
//        chore.description = "Call the bank"
//        chore.assignedBy = "Dany"
//        dbHandler!!.createChore(chore)
//
//        // Read from database
//
//        var chores: ArrayList<Chore> = dbHandler!!.readChores()
//        Log.d("Item:", chores.toString())

