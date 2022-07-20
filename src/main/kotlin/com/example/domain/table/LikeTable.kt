package com.example.domain.table

import org.jetbrains.exposed.dao.LongIdTable

object LikeTable : LongIdTable() {
    val userName = varchar(name = "user_name", length = 50)
    val userId = long(name = "user_id")
    val count = integer(name = "count")

}