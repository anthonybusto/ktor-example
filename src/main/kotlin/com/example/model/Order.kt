package com.example.model

@kotlinx.serialization.Serializable
data class Order(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val address: String,
    val city: String,
    val state: String,
    val zip: Int,
    val country: String,
    val total: Long,
    val userId: Int,
    val orderDate: String,
    )