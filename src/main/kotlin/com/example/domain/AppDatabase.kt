package com.example.domain

import com.example.domain.table.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.application.*

private const val DB_DRIVER = "org.postgresql.Driver"

fun Application.initDatabase() {

    val dbUrl = "jdbc:postgresql://localhost/postgres"
    val dbUserName = "admin"
    val dbPassword = "123456"
    Database.connect(url = dbUrl, driver = DB_DRIVER, user = dbUserName, password = dbPassword)

    transaction {
        val tables = arrayOf(UserTable)
//        SchemaUtils.drop(*tables.reversedArray())
        SchemaUtils.createMissingTablesAndColumns(*tables)
    }

}
