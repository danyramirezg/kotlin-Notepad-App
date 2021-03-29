package com.dany.notepadapp.model

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
}

fun main() {
    var chore = Chore()
    println(chore.description)
}