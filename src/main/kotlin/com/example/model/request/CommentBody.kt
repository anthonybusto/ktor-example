package com.example.model.request

@kotlinx.serialization.Serializable
data class CommentBody(
    val userId: Long,
    val userName: String,
    val content: String
)