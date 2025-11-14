package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.Purchase
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {
    suspend fun savePurchase(purchase: Purchase)
    fun observePurchases(from: Long, to: Long): Flow<List<Purchase>>
}
