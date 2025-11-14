package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.data.local.dao.SaleDao
import com.avtomatorgovli.core.data.mapper.toDomain
import com.avtomatorgovli.core.data.mapper.toEntity
import com.avtomatorgovli.core.domain.model.Sale
import com.avtomatorgovli.core.domain.repository.SaleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaleRepositoryImpl @Inject constructor(
    private val saleDao: SaleDao
) : SaleRepository {
    override suspend fun finalizeSale(sale: Sale) {
        val saleId = saleDao.insertSale(sale.toEntity())
        val items = sale.items.map { it.toEntity(saleId) }
        saleDao.insertItems(items)
    }

    override fun observeSales(from: Long, to: Long): Flow<List<Sale>> =
        saleDao.observeSales(from, to).map { entities ->
            entities.map { entity ->
                val items = saleDao.getItemsForSale(entity.id).map { it.toDomain() }
                entity.toDomain(items)
            }
        }

    override suspend fun nextReceiptNumber(): String {
        val count = saleDao.saleCount() + 1
        return "Чек-%05d".format(count)
    }
}
