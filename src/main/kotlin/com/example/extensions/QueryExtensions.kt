package com.example.extensions


import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityCache
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.selectAll

fun Query.selectSingle() = limit(1).firstOrNull()


fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.any(): Boolean {
    return this.all().any()
}

inline fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.any(predicate: (T) -> Boolean): Boolean {
    return this.all().any(predicate)
}


