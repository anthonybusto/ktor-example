package com.example.domain

import com.example.domain.table.ClipTable
import com.example.domain.table.OrderTable
import com.example.domain.table.RoomTable
import com.example.domain.table.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.application.*

private const val DB_DRIVER = "org.postgresql.Driver"

fun Application.initDatabase() {

    val dbUrl = "jdbc:postgresql://localhost/postgres"
    val dbUserName = "kingquanto"
    val dbPassword = "antmike1"
    Database.connect(url = dbUrl, driver = DB_DRIVER, user = dbUserName, password = dbPassword)

    transaction {
        val tables = arrayOf(UsersTable,RoomTable,OrderTable,ClipTable)
//        SchemaUtils.drop(*tables.reversedArray())
        SchemaUtils.createMissingTablesAndColumns(*tables)
    }

}
