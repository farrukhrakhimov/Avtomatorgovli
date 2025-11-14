package com.avtomatorgovli.core.data.mapper

import com.avtomatorgovli.core.data.local.entity.PurchaseEntity
import com.avtomatorgovli.core.data.local.entity.PurchaseItemEntity
import com.avtomatorgovli.core.domain.model.Purchase
import com.avtomatorgovli.core.domain.model.PurchaseItem
import com.avtomatorgovli.core.domain.model.SyncStatus

fun PurchaseEntity.toDomain(items: List<PurchaseItem>): Purchase = Purchase(
    id = id,
    localUuid = localUuid,
    documentNumber = documentNumber,
    dateTime = dateTime,
    supplierId = supplierId,
    totalAmount = totalAmount,
    notes = notes,
    syncStatus = SyncStatus.valueOf(syncStatus),
    items = items
)

fun Purchase.toEntity() = PurchaseEntity(
    id = if (id == 0L) 0 else id,
    localUuid = localUuid,
    documentNumber = documentNumber,
    dateTime = dateTime,
    supplierId = supplierId,
    totalAmount = totalAmount,
    notes = notes,
    syncStatus = syncStatus.name
)

fun PurchaseItemEntity.toDomain() = PurchaseItem(
    id = id,
    purchaseId = purchaseId,
    productId = productId,
    quantity = quantity,
    purchasePrice = purchasePrice,
    lineTotal = lineTotal
)

fun PurchaseItem.toEntity(purchaseIdOverride: Long) = PurchaseItemEntity(
    id = if (id == 0L) 0 else id,
    purchaseId = purchaseIdOverride,
    productId = productId,
    quantity = quantity,
    purchasePrice = purchasePrice,
    lineTotal = lineTotal
)
