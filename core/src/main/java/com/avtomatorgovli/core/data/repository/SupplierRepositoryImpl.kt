package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.data.local.dao.SupplierDao
import com.avtomatorgovli.core.data.mapper.toDomain
import com.avtomatorgovli.core.data.mapper.toEntity
import com.avtomatorgovli.core.domain.model.Supplier
import com.avtomatorgovli.core.domain.repository.SupplierRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupplierRepositoryImpl @Inject constructor(
    private val dao: SupplierDao
) : SupplierRepository {
    override fun observeSuppliers(query: String): Flow<List<Supplier>> = dao.observeSuppliers(query).map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun upsertSupplier(supplier: Supplier) {
        dao.upsert(supplier.toEntity())
    }
}
