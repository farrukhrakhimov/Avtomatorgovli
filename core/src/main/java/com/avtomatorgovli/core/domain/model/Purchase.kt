package com.avtomatorgovli.core.domain.model

data class Purchase(
    val id: Long,
    val localUuid: String,
    val documentNumber: String,
    val dateTime: Long,
    val supplierId: Long,
    val totalAmount: Double,
    val notes: String?,
    val syncStatus: SyncStatus,
    val items: List<PurchaseItem>
)

data class PurchaseItem(
    val id: Long,
    val purchaseId: Long,
    val productId: Long,
    val quantity: Double,
    val purchasePrice: Double,
    val lineTotal: Double
)
