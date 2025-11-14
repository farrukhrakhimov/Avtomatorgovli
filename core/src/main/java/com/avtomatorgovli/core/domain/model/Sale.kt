package com.avtomatorgovli.core.domain.model

data class Sale(
    val id: Long,
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
    val syncStatus: SyncStatus,
    val items: List<SaleItem>
)

data class SaleItem(
    val id: Long,
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

enum class SyncStatus { PENDING, SYNCED, FAILED }
