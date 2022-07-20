package com.example.model

@kotlinx.serialization.Serializable
data class Clip(
    val id: Long,
    val url: String,
    val likes: Int?,
    val comments: String?,
    val description: String,
//    val userId: Long,
//    val userName: String,
    val user: User
)
