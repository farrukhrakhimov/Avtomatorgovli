package com.avtomatorgovli.feature.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.DashboardMetrics
import com.avtomatorgovli.core.domain.model.Sale
import com.avtomatorgovli.core.domain.usecase.ObserveDashboardMetricsUseCase
import com.avtomatorgovli.core.domain.usecase.ObserveSalesReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class ReportsViewModel @Inject constructor(
    observeDashboardMetricsUseCase: ObserveDashboardMetricsUseCase,
    observeSalesReportUseCase: ObserveSalesReportUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        observeDashboardMetricsUseCase().onEach { metrics ->
            _uiState.value = _uiState.value.copy(metrics = metrics)
        }.launchIn(viewModelScope)
        val now = System.currentTimeMillis()
        observeSalesReportUseCase(now - 24 * 60 * 60 * 1000, now).onEach { sales ->
            _uiState.value = _uiState.value.copy(latestSales = sales)
        }.launchIn(viewModelScope)
    }
}

data class ReportsUiState(
    val metrics: DashboardMetrics? = null,
    val latestSales: List<Sale> = emptyList()
)
