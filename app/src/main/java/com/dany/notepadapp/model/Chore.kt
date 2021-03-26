package com.dany.notepadapp.model

class Chore {

    var choreName: String? = null
    var description: String? = null
    var assignedBy: String? = null
    var id: Int? = null

    constructor() {
        this.choreName = choreName
        this.description = description
        this.assignedBy = assignedBy
        this.id = id
    }
}

fun main() {
    var chore = Chore()
    println(chore.description)
}