package com.avtomatorgovli.core.domain.model

data class ProductCategory(
    val id: Long,
    val name: String,
    val description: String?,
    val isDeleted: Boolean,
    val updatedAt: Long
)
