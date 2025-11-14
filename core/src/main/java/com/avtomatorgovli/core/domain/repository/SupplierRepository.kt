package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.Supplier
import kotlinx.coroutines.flow.Flow

interface SupplierRepository {
    fun observeSuppliers(query: String = ""): Flow<List<Supplier>>
    suspend fun upsertSupplier(supplier: Supplier)
}
