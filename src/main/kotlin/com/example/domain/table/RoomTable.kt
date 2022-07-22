package com.example.domain.table

import org.jetbrains.exposed.dao.LongIdTable

object RoomTable : LongIdTable() {
    val channelName = varchar(name = "channel_name", length = 42)
    val uid = long(name = "uid")
    val createdBy = reference(name = "createdBy", foreign = UsersTable)
//    val participants = reference(name = "participants", foreign = UsersTable)
}
