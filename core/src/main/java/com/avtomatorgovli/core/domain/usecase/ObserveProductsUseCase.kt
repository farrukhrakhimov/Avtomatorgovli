package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(query: String = "", categoryId: Long? = null, onlyActive: Boolean = true): Flow<List<Product>> =
        productRepository.observeProducts(query, categoryId, onlyActive)
}
