package com.dany.notepadapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ProgressBar
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

        saveTask.setOnClickListener{

            // If the fields aren't empty, save them in the database

            if(!TextUtils.isEmpty(titleId.text.toString())
                            && !TextUtils.isEmpty(assignedId.text.toString())
                    && !TextUtils.isEmpty(descriptionId.text.toString())) {

                // Save to database

                var chore = Chore()
                chore.choreName = titleId.text.toString()
                chore.description = descriptionId.text.toString()
                chore.assignedBy = assignedId.text.toString()

                saveToDataBase(chore)
                startActivity(Intent(this, ChoreListActivity::class.java))
            } else{
                Toast.makeText(this, "Please complete the empty fields", Toast.LENGTH_LONG).show()
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

    }

    fun saveToDataBase(chore: Chore){
        dbHandler!!.createChore(chore)
    }

}