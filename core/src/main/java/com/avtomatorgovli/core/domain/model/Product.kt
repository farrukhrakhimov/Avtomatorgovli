package com.avtomatorgovli.core.domain.model

data class Product(
    val id: Long,
    val name: String,
    val sku: String?,
    val barcode: String?,
    val categoryId: Long,
    val unitOfMeasure: String,
    val purchasePrice: Double,
    val sellingPrice: Double,
    val taxRatePercent: Double,
    val isActive: Boolean,
    val minStockLevel: Double,
    val currentStock: Double,
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean
)
