package com.example.model

data class User(
    val id: Long,
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val city: String,
    val age: Int
)