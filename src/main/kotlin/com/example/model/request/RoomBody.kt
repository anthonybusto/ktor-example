package com.example.model.request

import com.example.model.User

@kotlinx.serialization.Serializable
data class RoomBody(
    val id: Long? = null,
    val channelName: String,
    val uid: Long,
    val createdByUserId: Long
)