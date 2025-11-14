package com.avtomatorgovli.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "product_categories")
data class ProductCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String?,
    val isDeleted: Boolean,
    val updatedAt: Long
)

@Entity(
    tableName = "products",
    indices = [Index("barcode"), Index("name")]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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
