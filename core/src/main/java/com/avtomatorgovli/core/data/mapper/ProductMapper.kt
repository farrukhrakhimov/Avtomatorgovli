package com.avtomatorgovli.core.data.mapper

import com.avtomatorgovli.core.data.local.entity.ProductCategoryEntity
import com.avtomatorgovli.core.data.local.entity.ProductEntity
import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.model.ProductCategory

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    name = name,
    sku = sku,
    barcode = barcode,
    categoryId = categoryId,
    unitOfMeasure = unitOfMeasure,
    purchasePrice = purchasePrice,
    sellingPrice = sellingPrice,
    taxRatePercent = taxRatePercent,
    isActive = isActive,
    minStockLevel = minStockLevel,
    currentStock = currentStock,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isDeleted = isDeleted
)

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = if (id == 0L) 0 else id,
    name = name,
    sku = sku,
    barcode = barcode,
    categoryId = categoryId,
    unitOfMeasure = unitOfMeasure,
    purchasePrice = purchasePrice,
    sellingPrice = sellingPrice,
    taxRatePercent = taxRatePercent,
    isActive = isActive,
    minStockLevel = minStockLevel,
    currentStock = currentStock,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isDeleted = isDeleted
)

fun ProductCategoryEntity.toDomain() = ProductCategory(id, name, description, isDeleted, updatedAt)

fun ProductCategory.toEntity() = ProductCategoryEntity(
    id = if (id == 0L) 0 else id,
    name = name,
    description = description,
    isDeleted = isDeleted,
    updatedAt = updatedAt
)
