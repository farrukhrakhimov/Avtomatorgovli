package com.avtomatorgovli.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avtomatorgovli.core.data.local.dao.CustomerDao
import com.avtomatorgovli.core.data.local.dao.ProductDao
import com.avtomatorgovli.core.data.local.dao.PurchaseDao
import com.avtomatorgovli.core.data.local.dao.SaleDao
import com.avtomatorgovli.core.data.local.dao.SettingsDao
import com.avtomatorgovli.core.data.local.dao.StockDao
import com.avtomatorgovli.core.data.local.dao.SupplierDao
import com.avtomatorgovli.core.data.local.dao.UserDao
import com.avtomatorgovli.core.data.local.entity.AppSettingEntity
import com.avtomatorgovli.core.data.local.entity.CustomerEntity
import com.avtomatorgovli.core.data.local.entity.LoyaltyTransactionEntity
import com.avtomatorgovli.core.data.local.entity.ProductCategoryEntity
import com.avtomatorgovli.core.data.local.entity.ProductEntity
import com.avtomatorgovli.core.data.local.entity.PurchaseEntity
import com.avtomatorgovli.core.data.local.entity.PurchaseItemEntity
import com.avtomatorgovli.core.data.local.entity.SaleEntity
import com.avtomatorgovli.core.data.local.entity.SaleItemEntity
import com.avtomatorgovli.core.data.local.entity.StockMovementEntity
import com.avtomatorgovli.core.data.local.entity.SupplierEntity
import com.avtomatorgovli.core.data.local.entity.UserEntity

@Database(
    entities = [
        ProductCategoryEntity::class,
        ProductEntity::class,
        CustomerEntity::class,
        SupplierEntity::class,
        UserEntity::class,
        SaleEntity::class,
        SaleItemEntity::class,
        PurchaseEntity::class,
        PurchaseItemEntity::class,
        StockMovementEntity::class,
        LoyaltyTransactionEntity::class,
        AppSettingEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class RetailDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun customerDao(): CustomerDao
    abstract fun supplierDao(): SupplierDao
    abstract fun userDao(): UserDao
    abstract fun saleDao(): SaleDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun stockDao(): StockDao
    abstract fun settingsDao(): SettingsDao
}
