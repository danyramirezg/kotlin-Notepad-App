package com.dany.notepadapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.dany.notepadapp.model.*
import java.sql.Time
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChoresDatabaseHandler (context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Create table:
        var CREATE_CHORE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                        KEY_CHORE_NAME + " TEXT," +
                        KEY_CHORE_DESCRIPTION + " TEXT," +
                        KEY_CHORE_ASSIGNED_BY + " TEXT," +
                        KEY_CHORE_ASSIGNED_TIME + " LONG" + ")"

        db?.execSQL(CREATE_CHORE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        // Create table again
        onCreate(db)

    }

    // Create a CRUD:

    fun createChore(chore: Chore) {

        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_DESCRIPTION, chore.description)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)

        // To debugging
        Log.d("DATA INSERTED", "SUCCESS")

        db.close()
    }

    fun readChores(): ArrayList<Chore> {

        var db: SQLiteDatabase = readableDatabase
        var list: ArrayList<Chore> = ArrayList()

        // Select chores from the table:
        var selectAll = "SELECT * FROM $TABLE_NAME"
        var cursor: Cursor = db.rawQuery(selectAll, null)

        // Looping through the chores
        if (cursor.moveToFirst()) {
            do {
                var chore = Chore()

                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
                chore.description = cursor.getString(cursor.getColumnIndex(KEY_CHORE_DESCRIPTION))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
                chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

                list.add(chore)
            } while (cursor.moveToNext())
        }
        return list
    }


//        var cursor: Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_CHORE_NAME, KEY_CHORE_DESCRIPTION,
//                            KEY_CHORE_ASSIGNED_BY, KEY_CHORE_ASSIGNED_TIME),
//                            KEY_ID + "=?", arrayOf(id.toString()),
//                    null, null, null, null)
//
//        if (cursor != null)
//            cursor.moveToFirst()
//
//        var chore = Chore()
//            chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
//            chore.description = cursor.getString(cursor.getColumnIndex(KEY_CHORE_DESCRIPTION))
//            chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_DESCRIPTION))
//            chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))


    // format date, to get something like: Mar 29 2021

//        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
//        var formattedDate = dateFormat.format(
//                Date(cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time)
//
//        return chore
//}


    fun updateChore(chore: Chore): Int {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_DESCRIPTION, chore.description)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        return db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(chore.id.toString()))
    }

    fun deleteChore(chore: Chore){
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(chore.id.toString()))
        db.close()
    }

    fun getChoresCount(): Int{
        var db: SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * FROM $TABLE_NAME"
        var cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count

    }
}
