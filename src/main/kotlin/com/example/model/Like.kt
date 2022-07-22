package com.example.model

@kotlinx.serialization.Serializable
data class Like(
    val id: Long,
    val userId: Long,
    val userName: String,
    val count: Int
)