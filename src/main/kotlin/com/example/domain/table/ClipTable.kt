package com.example.domain.table

import org.jetbrains.exposed.dao.LongIdTable

object ClipTable : LongIdTable() {
//    val userName = varchar(name = "user_name", length = 42).references(UsersTable.userName)


    val user = reference(name = "user", UsersTable)
    val url = varchar(name = "url", length = 75)
    val likes = integer(name = "likes",).nullable()
    val comments = varchar(name = "comments", length = 140).nullable()

    val description = varchar(name = "description", length = 140)
}