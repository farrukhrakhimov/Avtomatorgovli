package com.avtomatorgovli.core.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "stock_movements")
data class StockMovementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val dateTime: Long,
    val quantityChange: Double,
    val sourceType: String,
    val sourceId: Long?,
    val notes: String?
)

@Entity(tableName = "loyalty_transactions")
data class LoyaltyTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerId: Long,
    val dateTime: Long,
    val pointsChange: Double,
    val sourceType: String,
    val sourceId: Long?,
    val notes: String?
)

@Entity(tableName = "app_settings")
data class AppSettingEntity(
    @PrimaryKey val key: String,
    val value: String
)
