package com.example.domain.dao

import com.example.domain.table.RoomTable
import com.example.model.Room
import com.example.model.request.RoomBody
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.deleteAll


class  RoomDao(id: EntityID<Long>) : LongEntity(id) {
    var channelName by RoomTable.channelName
    var uid by RoomTable.uid
    var createdBy by UsersDao referencedOn RoomTable.createdBy
//    val participants  by UsersDao referrersOn RoomTable.participants


    companion object : LongEntityClass<RoomDao>(RoomTable) {

        fun deleteAll() = this.table.deleteAll()

        fun insert(model: RoomBody): Room = this.new {
            channelName = model.channelName
            uid = model.uid
            createdBy = UsersDao.findById(model.createdByUserId)
                ?: throw Exception("Couldn't create Room object! No user found with ID=${model.createdByUserId}")

        }.toRoom()

        fun insertAll(list: List<RoomBody>): List<Room> = list.map { insert(it) }
    }
}

fun RoomDao.toRoom() = Room(
    id = id.value,
    createdByUserName = createdBy.userName,
    channelName = channelName,
    uid = uid,
    createdByUserId = createdBy.id.value
)