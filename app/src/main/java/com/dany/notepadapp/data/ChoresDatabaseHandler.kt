package com.dany.notepadapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.dany.notepadapp.model.*
import java.sql.Time
import java.util.*


class ChoresDatabaseHandler (context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        // Create table:
        var CREATE_CHORE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                        KEY_CHORE_NAME + " TEXT," +
                        KEY_CHORE_DESCRIPTION + " TEXT," +
                        KEY_CHORE_ASSIGNED_BY + " TEXT," +
                        KEY_CHORE_ASSIGNED_TIME + " LONG" +")"

        db?.execSQL(CREATE_CHORE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        // Create table again
        onCreate(db)

    }

    // Create a CRUD:

    fun createChore(chore: Chore){

        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_DESCRIPTION, chore.description)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)

        // To debugging
        Log.d("DATA INSERTED","SUCCESS")

        db.close()
    }

}