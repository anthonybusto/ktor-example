package com.example.model

@kotlinx.serialization.Serializable
data class Room(
    val id: Long,
    val channelName: String,
    val uid: Long,
    val createdByUserId: Long,
    val createdByUserName: String,
    val participants: List<User>? = emptyList()
)