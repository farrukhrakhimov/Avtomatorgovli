package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.DashboardMetrics
import com.avtomatorgovli.core.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDashboardMetricsUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(): Flow<DashboardMetrics> = reportRepository.observeDashboardMetrics()
}
