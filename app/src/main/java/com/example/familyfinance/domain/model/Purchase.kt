package com.example.familyfinance.domain.model

data class Purchase(
    val id: Long,
    val productId: Long,
    val groupId: Long,
    val price: Double,
    val quantity: Double,
    val date: Long,
    val note: String
)
