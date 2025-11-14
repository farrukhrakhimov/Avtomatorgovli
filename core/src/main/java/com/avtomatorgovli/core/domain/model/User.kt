package com.avtomatorgovli.core.domain.model

enum class UserRole { ADMIN, MANAGER, CASHIER }

data class User(
    val id: Long,
    val username: String,
    val displayName: String,
    val role: UserRole,
    val isActive: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
