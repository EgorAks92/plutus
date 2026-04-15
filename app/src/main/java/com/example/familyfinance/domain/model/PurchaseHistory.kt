package com.example.familyfinance.domain.model

data class PurchaseHistory(
    val id: Long,
    val productId: Long,
    val groupId: Long,
    val productName: String,
    val groupName: String,
    val price: Double,
    val quantity: Double,
    val date: Long,
    val note: String,
    val total: Double
)
