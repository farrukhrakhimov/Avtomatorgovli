package com.avtomatorgovli.core.domain.model

data class Customer(
    val id: Long,
    val fullName: String,
    val phone: String?,
    val email: String?,
    val address: String?,
    val cardNumber: String?,
    val loyaltyPointsBalance: Double,
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean
)
