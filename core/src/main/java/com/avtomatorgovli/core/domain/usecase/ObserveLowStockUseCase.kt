package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLowStockUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> = repository.observeLowStockProducts()
}
