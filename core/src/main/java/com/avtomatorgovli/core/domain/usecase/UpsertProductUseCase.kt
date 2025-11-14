package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.repository.ProductRepository
import javax.inject.Inject

class UpsertProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(product: Product) = productRepository.upsertProduct(product)
}
