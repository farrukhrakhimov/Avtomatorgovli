package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.data.local.dao.StockDao
import com.avtomatorgovli.core.data.local.entity.StockMovementEntity
import com.avtomatorgovli.core.domain.model.StockMovement
import com.avtomatorgovli.core.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockDao: StockDao
) : StockRepository {
    override suspend fun registerMovement(movement: StockMovement) {
        stockDao.insertMovement(
            StockMovementEntity(
                id = 0L,
                productId = movement.productId,
                dateTime = movement.dateTime,
                quantityChange = movement.quantityChange,
                sourceType = movement.sourceType,
                sourceId = movement.sourceId,
                notes = movement.notes
            )
        )
    }

    override fun observeMovements(productId: Long): Flow<List<StockMovement>> =
        stockDao.observeMovements(productId).map { list ->
            list.map {
                StockMovement(
                    id = it.id,
                    productId = it.productId,
                    dateTime = it.dateTime,
                    quantityChange = it.quantityChange,
                    sourceType = it.sourceType,
                    sourceId = it.sourceId,
                    notes = it.notes
                )
            }
        }
}
