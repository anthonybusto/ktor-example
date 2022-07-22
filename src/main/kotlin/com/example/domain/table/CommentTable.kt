package com.example.domain.table

import org.jetbrains.exposed.dao.LongIdTable

object CommentTable : LongIdTable() {
    val userName = varchar(name = "user_name", length = 50)
    val userId = long(name = "user_id")
    val content = varchar(name = "content", length = 140)

}