package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.domain.model.DashboardMetrics
import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.model.Sale
import com.avtomatorgovli.core.domain.repository.ProductRepository
import com.avtomatorgovli.core.domain.repository.ReportRepository
import com.avtomatorgovli.core.domain.repository.SaleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepositoryImpl @Inject constructor(
    private val saleRepository: SaleRepository,
    private val productRepository: ProductRepository
) : ReportRepository {
    override fun observeDashboardMetrics(): Flow<DashboardMetrics> {
        val now = System.currentTimeMillis()
        val startOfDay = now - now % (24 * 60 * 60 * 1000)
        val startOfWeek = startOfDay - 6 * 24 * 60 * 60 * 1000
        val todayFlow = saleRepository.observeSales(startOfDay, Long.MAX_VALUE)
        val weekFlow = saleRepository.observeSales(startOfWeek, Long.MAX_VALUE)
        val lowStockFlow = productRepository.observeLowStockProducts()
        val productsFlow = productRepository.observeProducts()
        return combine(todayFlow, weekFlow, lowStockFlow, productsFlow) { todaySales, weekSales, lowStock, products ->
            val productsById = products.associateBy { it.id }
            val productTotals = weekSales.flatMap { it.items }
                .groupBy { it.productId }
                .mapValues { entry -> entry.value.sumOf { it.lineTotalWithTax } }
            val topProducts = productTotals.entries
                .sortedByDescending { it.value }
                .mapNotNull { productsById[it.key]?.copy(currentStock = it.value) }
                .take(5)
            DashboardMetrics(
                todaySales = todaySales.sumOf { it.totalWithTax },
                weekSales = weekSales.sumOf { it.totalWithTax },
                lowStockCount = lowStock.size,
                topProducts = topProducts
            )
        }
    }

    override fun observeSalesByCashier(from: Long, to: Long): Flow<Map<Long, List<Sale>>> =
        saleRepository.observeSales(from, to).map { sales -> sales.groupBy { it.userId } }

    override fun observeSalesByProduct(from: Long, to: Long): Flow<Map<Product, Double>> {
        return combine(saleRepository.observeSales(from, to), productRepository.observeProducts()) { sales, products ->
            val productsById = products.associateBy { it.id }
            val amounts = sales.flatMap { it.items }
                .groupBy { it.productId }
                .mapValues { entry -> entry.value.sumOf { it.lineTotalWithTax } }
            amounts.mapNotNull { (productId, amount) ->
                val product = productsById[productId]
                if (product != null) product to amount else null
            }.toMap()
        }
    }
}
