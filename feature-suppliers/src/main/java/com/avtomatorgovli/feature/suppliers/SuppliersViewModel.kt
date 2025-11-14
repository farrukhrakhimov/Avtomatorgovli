package com.avtomatorgovli.feature.suppliers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.Supplier
import com.avtomatorgovli.core.domain.usecase.ObserveSuppliersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class SuppliersViewModel @Inject constructor(
    observeSuppliersUseCase: ObserveSuppliersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SuppliersUiState())
    val state: StateFlow<SuppliersUiState> = _state.asStateFlow()

    init {
        observeSuppliersUseCase().onEach { suppliers ->
            _state.value = SuppliersUiState(suppliers)
        }.launchIn(viewModelScope)
    }
}

data class SuppliersUiState(val suppliers: List<Supplier> = emptyList())
