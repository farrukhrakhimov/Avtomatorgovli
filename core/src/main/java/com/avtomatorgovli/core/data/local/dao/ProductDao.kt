package com.avtomatorgovli.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avtomatorgovli.core.data.local.entity.ProductCategoryEntity
import com.avtomatorgovli.core.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query(
        "SELECT * FROM products WHERE (:query == '' OR name LIKE '%' || :query || '%' OR barcode = :query) AND (:categoryId IS NULL OR categoryId = :categoryId) AND (:onlyActive == 0 OR isActive = 1) AND isDeleted = 0 ORDER BY name"
    )
    fun observeProducts(query: String, categoryId: Long?, onlyActive: Int): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE barcode = :barcode LIMIT 1")
    suspend fun getByBarcode(barcode: String): ProductEntity?

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: Long): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProduct(product: ProductEntity)

    @Query("SELECT * FROM products WHERE currentStock <= minStockLevel AND isDeleted = 0 AND isActive = 1")
    fun observeLowStock(): Flow<List<ProductEntity>>

    @Query("UPDATE products SET currentStock = :stock, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateStock(id: Long, stock: Double, updatedAt: Long)

    @Query("SELECT * FROM product_categories WHERE isDeleted = 0 ORDER BY name")
    fun observeCategories(): Flow<List<ProductCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategory(category: ProductCategoryEntity)
}
