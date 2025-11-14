package com.avtomatorgovli.core.domain.model

data class Supplier(
    val id: Long,
    val name: String,
    val phone: String?,
    val email: String?,
    val address: String?,
    val notes: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean
)
