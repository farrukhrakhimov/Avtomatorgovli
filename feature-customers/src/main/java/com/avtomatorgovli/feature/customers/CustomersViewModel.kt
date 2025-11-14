package com.avtomatorgovli.feature.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.Customer
import com.avtomatorgovli.core.domain.usecase.ObserveCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class CustomersViewModel @Inject constructor(
    observeCustomersUseCase: ObserveCustomersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CustomersUiState())
    val uiState: StateFlow<CustomersUiState> = _uiState.asStateFlow()

    init {
        observeCustomersUseCase().onEach { customers ->
            _uiState.value = CustomersUiState(customers)
        }.launchIn(viewModelScope)
    }
}

data class CustomersUiState(val customers: List<Customer> = emptyList())
