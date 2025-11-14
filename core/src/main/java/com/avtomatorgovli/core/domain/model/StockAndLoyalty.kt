package com.avtomatorgovli.core.domain.model

data class StockMovement(
    val id: Long,
    val productId: Long,
    val dateTime: Long,
    val quantityChange: Double,
    val sourceType: String,
    val sourceId: Long?,
    val notes: String?
)

data class LoyaltyTransaction(
    val id: Long,
    val customerId: Long,
    val dateTime: Long,
    val pointsChange: Double,
    val sourceType: String,
    val sourceId: Long?,
    val notes: String?
)

data class AppSetting(
    val key: String,
    val value: String
)
