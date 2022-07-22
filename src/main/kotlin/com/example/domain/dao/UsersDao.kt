package com.example.domain.dao

import com.example.domain.table.UsersTable
import com.example.model.User
import com.example.model.request.UserBody
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.deleteAll


class  UsersDao(id: EntityID<Long>) : LongEntity(id) {
    var userName by UsersTable.userName
    var firstName by UsersTable.firstName
    var lastName by UsersTable.lastName
    var city by UsersTable.city
    var age by UsersTable.age
    var avatar by UsersTable.avatar
    var banner by UsersTable.banner


    companion object : LongEntityClass<UsersDao>(UsersTable) {

        fun deleteAll() = this.table.deleteAll()

        fun insert(model: UserBody): User = this.new {
            userName = model.userName
            firstName = model.firstName
            lastName = model.lastName
            city = model.city
            age = model.age
            avatar = model.avatar
            banner = model.banner

        }.toUser()

        fun insertAll(list: List<UserBody>): List<User> = list.map { insert(it) }
    }
}

fun UsersDao.toUser() = User(
    id = id.value,
    userName = userName,
    firstName = firstName,
    lastName = lastName,
    city = city,
    age = age,
    avatar = avatar,
    banner = banner

)