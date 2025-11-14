package com.avtomatorgovli.feature.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.usecase.ObserveLowStockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class InventoryViewModel @Inject constructor(
    observeLowStockUseCase: ObserveLowStockUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(InventoryUiState())
    val state: StateFlow<InventoryUiState> = _state.asStateFlow()

    init {
        observeLowStockUseCase().onEach { products ->
            _state.value = InventoryUiState(products)
        }.launchIn(viewModelScope)
    }
}

data class InventoryUiState(val lowStock: List<Product> = emptyList())
