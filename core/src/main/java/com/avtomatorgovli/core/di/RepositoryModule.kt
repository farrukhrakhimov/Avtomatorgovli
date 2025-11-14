package com.avtomatorgovli.core.di

import com.avtomatorgovli.core.domain.repository.AuthRepository
import com.avtomatorgovli.core.domain.repository.CustomerRepository
import com.avtomatorgovli.core.domain.repository.ProductRepository
import com.avtomatorgovli.core.domain.repository.PurchaseRepository
import com.avtomatorgovli.core.domain.repository.ReportRepository
import com.avtomatorgovli.core.domain.repository.SaleRepository
import com.avtomatorgovli.core.domain.repository.SettingsRepository
import com.avtomatorgovli.core.domain.repository.StockRepository
import com.avtomatorgovli.core.domain.repository.SupplierRepository
import com.avtomatorgovli.core.data.repository.AuthRepositoryImpl
import com.avtomatorgovli.core.data.repository.CustomerRepositoryImpl
import com.avtomatorgovli.core.data.repository.ProductRepositoryImpl
import com.avtomatorgovli.core.data.repository.PurchaseRepositoryImpl
import com.avtomatorgovli.core.data.repository.ReportRepositoryImpl
import com.avtomatorgovli.core.data.repository.SaleRepositoryImpl
import com.avtomatorgovli.core.data.repository.SettingsRepositoryImpl
import com.avtomatorgovli.core.data.repository.StockRepositoryImpl
import com.avtomatorgovli.core.data.repository.SupplierRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
    @Binds abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
    @Binds abstract fun bindCustomerRepository(impl: CustomerRepositoryImpl): CustomerRepository
    @Binds abstract fun bindSupplierRepository(impl: SupplierRepositoryImpl): SupplierRepository
    @Binds abstract fun bindSaleRepository(impl: SaleRepositoryImpl): SaleRepository
    @Binds abstract fun bindPurchaseRepository(impl: PurchaseRepositoryImpl): PurchaseRepository
    @Binds abstract fun bindStockRepository(impl: StockRepositoryImpl): StockRepository
    @Binds abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
    @Binds abstract fun bindReportRepository(impl: ReportRepositoryImpl): ReportRepository
}
