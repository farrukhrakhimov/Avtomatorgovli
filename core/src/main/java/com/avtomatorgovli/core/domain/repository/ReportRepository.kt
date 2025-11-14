package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.DashboardMetrics
import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.model.Sale
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun observeDashboardMetrics(): Flow<DashboardMetrics>
    fun observeSalesByCashier(from: Long, to: Long): Flow<Map<Long, List<Sale>>>
    fun observeSalesByProduct(from: Long, to: Long): Flow<Map<Product, Double>>
}
