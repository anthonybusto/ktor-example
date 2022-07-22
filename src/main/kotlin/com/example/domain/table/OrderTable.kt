package com.example.domain.table

import org.jetbrains.exposed.dao.LongIdTable

object OrderTable : LongIdTable() {
    val firstName = varchar(name = "first_name", length = 50)
    val lastName = varchar(name = "last_name", length = 50)
    val address = varchar(name = "address", length = 100)
    val city = varchar(name = "city", length = 100)
    val state = varchar(name = "state", length = 2)
    val country = varchar(name = "country", length = 100)
    val zip = integer(name = "zip")
    val total = long(name = "total")
    val userId = integer(name = "user_id")
    val orderDate = varchar(name = "order_date", length = 100)
}

