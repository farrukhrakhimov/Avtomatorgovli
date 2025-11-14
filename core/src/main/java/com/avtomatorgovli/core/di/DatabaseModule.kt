package com.avtomatorgovli.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.avtomatorgovli.core.data.datastore.SessionDataStore
import com.avtomatorgovli.core.data.local.RetailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RetailDatabase =
        Room.databaseBuilder(context, RetailDatabase::class.java, "retail.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideProductDao(db: RetailDatabase) = db.productDao()
    @Provides fun provideCustomerDao(db: RetailDatabase) = db.customerDao()
    @Provides fun provideSupplierDao(db: RetailDatabase) = db.supplierDao()
    @Provides fun provideUserDao(db: RetailDatabase) = db.userDao()
    @Provides fun provideSaleDao(db: RetailDatabase) = db.saleDao()
    @Provides fun providePurchaseDao(db: RetailDatabase) = db.purchaseDao()
    @Provides fun provideStockDao(db: RetailDatabase) = db.stockDao()
    @Provides fun provideSettingsDao(db: RetailDatabase) = db.settingsDao()

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): DataStore<Preferences> = context.sessionDataStore
}
