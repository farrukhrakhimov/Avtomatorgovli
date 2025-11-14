package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.StockMovement
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun registerMovement(movement: StockMovement)
    fun observeMovements(productId: Long): Flow<List<StockMovement>>
}
