package com.example.domain.table

import org.jetbrains.exposed.dao.LongIdTable

object UsersTable : LongIdTable() {
    val userName = varchar(name = "user_name", length = 42)
    val firstName = varchar(name = "first_name", length = 50).nullable()
    val lastName = varchar(name = "last_name", length = 50).nullable()
    val city = varchar(name = "city", length = 100)
    val age = integer(name = "age")
    val avatar = varchar(name="avatar", length = 100).nullable()
    val banner = varchar(name="banner", length = 100).nullable()

}

