package com.example.domain.dao

import com.example.domain.table.UserTable
import com.example.model.User
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.deleteAll


class  UserDao(id: EntityID<Long>) : LongEntity(id) {
    var userName by UserTable.userName
    var firstName by UserTable.firstName
    var lastName by UserTable.lastName
    var city by UserTable.city
    var age by UserTable.age


    companion object : LongEntityClass<UserDao>(UserTable) {

        fun deleteAll() = this.table.deleteAll()

        fun insert(model: User) = this.new {
            userName = model.userName
            firstName = model.firstName
            lastName = model.lastName
            city = model.city
            age = model.age
        }.toUser()

        fun insertAll(list: List<User>): List<User> = list.map { insert(it) }
    }
}

fun UserDao.toUser() = User(
    id = id.value,
    userName = userName,
    firstName = firstName,
    lastName = lastName,
    city = city,
    age = age,

)