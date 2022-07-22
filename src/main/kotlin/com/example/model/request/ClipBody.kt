package com.example.model.request

@kotlinx.serialization.Serializable
data class ClipBody(
    val id: Long? = null,
    val url: String,
    val likes: Int?,
    val comments: String?,
    val description: String,
    val userId: Long,
    val userName: String,
)
