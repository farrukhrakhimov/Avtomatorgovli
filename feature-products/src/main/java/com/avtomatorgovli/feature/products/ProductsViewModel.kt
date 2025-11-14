package com.avtomatorgovli.feature.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.usecase.ObserveProductsUseCase
import com.avtomatorgovli.core.domain.usecase.UpsertProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class ProductsViewModel @Inject constructor(
    observeProductsUseCase: ObserveProductsUseCase,
    private val upsertProductUseCase: UpsertProductUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        observeProductsUseCase().onEach { products ->
            _uiState.value = _uiState.value.copy(products = products)
        }.launchIn(viewModelScope)
    }

    fun saveQuickProduct(name: String, price: Double) {
        val product = Product(
            id = 0L,
            name = name,
            sku = null,
            barcode = null,
            categoryId = 0,
            unitOfMeasure = "шт",
            purchasePrice = price * 0.7,
            sellingPrice = price,
            taxRatePercent = 12.0,
            isActive = true,
            minStockLevel = 1.0,
            currentStock = 0.0,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            isDeleted = false
        )
        viewModelScope.launch { upsertProductUseCase(product) }
    }
}

data class ProductsUiState(
    val products: List<Product> = emptyList()
)
