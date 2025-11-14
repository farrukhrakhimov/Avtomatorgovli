package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByBarcodeUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(barcode: String): Product? = repository.getProductByBarcode(barcode)
}
