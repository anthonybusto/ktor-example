package com.example.model

@kotlinx.serialization.Serializable
data class User(
    val id: Long,
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val city: String,
    val age: Int,
    val avatar:String?,
    val banner:String?
)



