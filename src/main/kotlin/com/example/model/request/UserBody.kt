package com.example.model.request

@kotlinx.serialization.Serializable
data class UserBody(
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val city: String,
    val age: Int,
    val avatar:String? = null,
    val banner:String? = null
)