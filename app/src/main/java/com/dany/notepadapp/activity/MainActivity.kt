package com.dany.notepadapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dany.notepadapp.R
import com.dany.notepadapp.data.ChoresDatabaseHandler
import com.dany.notepadapp.model.Chore

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)

        var chore = Chore()
        chore.choreName = "Things to do"
        chore.description = "Call the bank"
        chore.assignedBy = "Dany"
        dbHandler!!.createChore(chore)
    }
}