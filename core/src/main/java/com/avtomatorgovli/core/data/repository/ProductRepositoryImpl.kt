package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.data.local.dao.ProductDao
import com.avtomatorgovli.core.data.mapper.toDomain
import com.avtomatorgovli.core.data.mapper.toEntity
import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.model.ProductCategory
import com.avtomatorgovli.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductRepository {
    override fun observeProducts(query: String, categoryId: Long?, onlyActive: Boolean): Flow<List<Product>> =
        productDao.observeProducts(query, categoryId, if (onlyActive) 1 else 0).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun observeLowStockProducts(): Flow<List<Product>> =
        productDao.observeLowStock().map { list -> list.map { it.toDomain() } }

    override suspend fun getProductByBarcode(barcode: String): Product? = productDao.getByBarcode(barcode)?.toDomain()

    override suspend fun getProductById(id: Long): Product? = productDao.getById(id)?.toDomain()

    override suspend fun upsertProduct(product: Product) {
        productDao.upsertProduct(product.toEntity())
    }

    override suspend fun upsertCategory(category: ProductCategory) {
        productDao.upsertCategory(category.toEntity())
    }

    override fun observeCategories(): Flow<List<ProductCategory>> = productDao.observeCategories().map { list ->
        list.map { it.toDomain() }
    }
}
