package com.example.familyfinance.data.local.model

data class PurchaseHistoryItem(
    val id: Long,
    val productId: Long,
    val groupId: Long,
    val productName: String,
    val groupName: String,
    val price: Double,
    val quantity: Double,
    val date: Long,
    val note: String
) {
    val total: Double
        get() = price * quantity
}
