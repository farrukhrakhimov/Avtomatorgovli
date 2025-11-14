package com.avtomatorgovli.core.domain.model

data class DashboardMetrics(
    val todaySales: Double,
    val weekSales: Double,
    val lowStockCount: Int,
    val topProducts: List<Product>
)
