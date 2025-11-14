package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.data.local.dao.ProductDao
import com.avtomatorgovli.core.data.local.dao.PurchaseDao
import com.avtomatorgovli.core.data.mapper.toDomain
import com.avtomatorgovli.core.data.mapper.toEntity
import com.avtomatorgovli.core.domain.model.Purchase
import com.avtomatorgovli.core.domain.repository.PurchaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PurchaseRepositoryImpl @Inject constructor(
    private val purchaseDao: PurchaseDao,
    private val productDao: ProductDao
) : PurchaseRepository {
    override suspend fun savePurchase(purchase: Purchase) {
        val purchaseId = purchaseDao.insertPurchase(purchase.toEntity())
        purchaseDao.insertItems(purchase.items.map { it.toEntity(purchaseId) })
        purchase.items.forEach { item ->
            val existing = productDao.getById(item.productId)
            existing?.let {
                val updatedStock = it.currentStock + item.quantity
                productDao.updateStock(it.id, updatedStock, System.currentTimeMillis())
            }
        }
    }

    override fun observePurchases(from: Long, to: Long): Flow<List<Purchase>> =
        purchaseDao.observePurchases(from, to).map { list ->
            list.map { entity ->
                val items = purchaseDao.getItemsForPurchase(entity.id).map { it.toDomain() }
                entity.toDomain(items)
            }
        }
}
