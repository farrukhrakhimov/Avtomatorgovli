package com.avtomatorgovli.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sales",
    indices = [Index("receiptNumber"), Index("localUuid", unique = true)]
)
data class SaleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val localUuid: String,
    val receiptNumber: String,
    val dateTime: Long,
    val customerId: Long?,
    val userId: Long,
    val totalWithoutTax: Double,
    val totalTax: Double,
    val totalWithTax: Double,
    val discountAmount: Double,
    val paidCash: Double,
    val paidCard: Double,
    val paidOther: Double,
    val changeGiven: Double,
    val loyaltyPointsUsed: Double,
    val loyaltyPointsEarned: Double,
    val isRefund: Boolean,
    val syncStatus: String
)

@Entity(
    tableName = "sale_items",
    foreignKeys = [
        ForeignKey(
            entity = SaleEntity::class,
            parentColumns = ["id"],
            childColumns = ["saleId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("saleId"), Index("productId")]
)
data class SaleItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val saleId: Long,
    val productId: Long,
    val quantity: Double,
    val unitPrice: Double,
    val discountPercent: Double,
    val taxRatePercent: Double,
    val lineTotalWithoutTax: Double,
    val lineTax: Double,
    val lineTotalWithTax: Double
)
