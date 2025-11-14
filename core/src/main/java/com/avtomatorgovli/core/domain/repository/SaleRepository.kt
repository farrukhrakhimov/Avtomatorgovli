package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.Sale
import kotlinx.coroutines.flow.Flow

interface SaleRepository {
    suspend fun finalizeSale(sale: Sale)
    fun observeSales(from: Long, to: Long): Flow<List<Sale>>
    suspend fun nextReceiptNumber(): String
}
