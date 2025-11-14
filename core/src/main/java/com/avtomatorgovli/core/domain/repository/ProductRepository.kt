package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.model.ProductCategory
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeProducts(query: String = "", categoryId: Long? = null, onlyActive: Boolean = true): Flow<List<Product>>
    fun observeLowStockProducts(): Flow<List<Product>>
    suspend fun getProductByBarcode(barcode: String): Product?
    suspend fun getProductById(id: Long): Product?
    suspend fun upsertProduct(product: Product)
    suspend fun upsertCategory(category: ProductCategory)
    fun observeCategories(): Flow<List<ProductCategory>>
}
