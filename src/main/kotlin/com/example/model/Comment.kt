package com.example.model

@kotlinx.serialization.Serializable
data class Comment(
    val id: Long,
    val userId: Long,
    val userName: String,
    val content: String
)
