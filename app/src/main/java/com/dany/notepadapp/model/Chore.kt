package com.dany.notepadapp.model

import java.text.DateFormat
import java.util.*

class Chore {

    var choreName: String? = null
    var description: String? = null
    var assignedBy: String? = null
    var timeAssigned: Long? = null
    var id: Int? = null

    constructor() {
        this.choreName = choreName
        this.description = description
        this.assignedBy = assignedBy
        this.timeAssigned = timeAssigned
        this.id = id
    }

    fun showHumanDate(timeAssigned: Long): String{

        var dateFormat: DateFormat = DateFormat.getDateInstance()
        var formattedDate: String = dateFormat.format(Date(timeAssigned).time)

        return "Created: $formattedDate"
    }

    override fun toString(): String{
        return "Task(Task title=$choreName, Description=$description, assignedBy=$assignedBy," +
                "timeAssigned=$timeAssigned, id=$id)"
    }
}

fun main() {
    var chore = Chore()
    println(chore.description)
    println(chore.showHumanDate(30))
    println(chore.toString())
}