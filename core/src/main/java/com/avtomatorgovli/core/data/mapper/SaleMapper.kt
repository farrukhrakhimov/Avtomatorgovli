package com.avtomatorgovli.core.data.mapper

import com.avtomatorgovli.core.data.local.entity.SaleEntity
import com.avtomatorgovli.core.data.local.entity.SaleItemEntity
import com.avtomatorgovli.core.domain.model.Sale
import com.avtomatorgovli.core.domain.model.SaleItem
import com.avtomatorgovli.core.domain.model.SyncStatus

fun SaleEntity.toDomain(items: List<SaleItem>): Sale = Sale(
    id = id,
    localUuid = localUuid,
    receiptNumber = receiptNumber,
    dateTime = dateTime,
    customerId = customerId,
    userId = userId,
    totalWithoutTax = totalWithoutTax,
    totalTax = totalTax,
    totalWithTax = totalWithTax,
    discountAmount = discountAmount,
    paidCash = paidCash,
    paidCard = paidCard,
    paidOther = paidOther,
    changeGiven = changeGiven,
    loyaltyPointsUsed = loyaltyPointsUsed,
    loyaltyPointsEarned = loyaltyPointsEarned,
    isRefund = isRefund,
    syncStatus = SyncStatus.valueOf(syncStatus),
    items = items
)

fun Sale.toEntity() = SaleEntity(
    id = if (id == 0L) 0 else id,
    localUuid = localUuid,
    receiptNumber = receiptNumber,
    dateTime = dateTime,
    customerId = customerId,
    userId = userId,
    totalWithoutTax = totalWithoutTax,
    totalTax = totalTax,
    totalWithTax = totalWithTax,
    discountAmount = discountAmount,
    paidCash = paidCash,
    paidCard = paidCard,
    paidOther = paidOther,
    changeGiven = changeGiven,
    loyaltyPointsUsed = loyaltyPointsUsed,
    loyaltyPointsEarned = loyaltyPointsEarned,
    isRefund = isRefund,
    syncStatus = syncStatus.name
)

fun SaleItemEntity.toDomain() = SaleItem(
    id = id,
    saleId = saleId,
    productId = productId,
    quantity = quantity,
    unitPrice = unitPrice,
    discountPercent = discountPercent,
    taxRatePercent = taxRatePercent,
    lineTotalWithoutTax = lineTotalWithoutTax,
    lineTax = lineTax,
    lineTotalWithTax = lineTotalWithTax
)

fun SaleItem.toEntity(saleIdOverride: Long) = SaleItemEntity(
    id = if (id == 0L) 0 else id,
    saleId = saleIdOverride,
    productId = productId,
    quantity = quantity,
    unitPrice = unitPrice,
    discountPercent = discountPercent,
    taxRatePercent = taxRatePercent,
    lineTotalWithoutTax = lineTotalWithoutTax,
    lineTax = lineTax,
    lineTotalWithTax = lineTotalWithTax
)
