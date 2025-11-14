package com.avtomatorgovli.core.data.mapper

import com.avtomatorgovli.core.data.local.entity.CustomerEntity
import com.avtomatorgovli.core.data.local.entity.LoyaltyTransactionEntity
import com.avtomatorgovli.core.data.local.entity.SupplierEntity
import com.avtomatorgovli.core.data.local.entity.UserEntity
import com.avtomatorgovli.core.domain.model.Customer
import com.avtomatorgovli.core.domain.model.LoyaltyTransaction
import com.avtomatorgovli.core.domain.model.Supplier
import com.avtomatorgovli.core.domain.model.User
import com.avtomatorgovli.core.domain.model.UserRole

fun CustomerEntity.toDomain() = Customer(
    id = id,
    fullName = fullName,
    phone = phone,
    email = email,
    address = address,
    cardNumber = cardNumber,
    loyaltyPointsBalance = loyaltyPointsBalance,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isDeleted = isDeleted
)

fun Customer.toEntity() = CustomerEntity(
    id = if (id == 0L) 0 else id,
    fullName = fullName,
    phone = phone,
    email = email,
    address = address,
    cardNumber = cardNumber,
    loyaltyPointsBalance = loyaltyPointsBalance,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isDeleted = isDeleted
)

fun SupplierEntity.toDomain() = Supplier(id, name, phone, email, address, notes, createdAt, updatedAt, isDeleted)
fun Supplier.toEntity() = SupplierEntity(
    id = if (id == 0L) 0 else id,
    name = name,
    phone = phone,
    email = email,
    address = address,
    notes = notes,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isDeleted = isDeleted
)

fun UserEntity.toDomain() = User(
    id = id,
    username = username,
    displayName = displayName,
    role = UserRole.valueOf(role),
    isActive = isActive,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun LoyaltyTransactionEntity.toDomain() = LoyaltyTransaction(
    id = id,
    customerId = customerId,
    dateTime = dateTime,
    pointsChange = pointsChange,
    sourceType = sourceType,
    sourceId = sourceId,
    notes = notes
)

fun LoyaltyTransaction.toEntity() = LoyaltyTransactionEntity(
    id = if (id == 0L) 0 else id,
    customerId = customerId,
    dateTime = dateTime,
    pointsChange = pointsChange,
    sourceType = sourceType,
    sourceId = sourceId,
    notes = notes
)
