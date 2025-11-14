package com.avtomatorgovli.feature.pos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.Customer
import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.model.User
import com.avtomatorgovli.core.domain.usecase.FinalizeSaleUseCase
import com.avtomatorgovli.core.domain.usecase.GetProductByBarcodeUseCase
import com.avtomatorgovli.core.domain.usecase.ObserveActiveUserUseCase
import com.avtomatorgovli.core.domain.usecase.ObserveCustomersUseCase
import com.avtomatorgovli.core.domain.usecase.ObserveProductsUseCase
import com.avtomatorgovli.core.domain.usecase.SalePayments
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class PosViewModel @Inject constructor(
    observeProductsUseCase: ObserveProductsUseCase,
    observeCustomersUseCase: ObserveCustomersUseCase,
    observeActiveUserUseCase: ObserveActiveUserUseCase,
    private val finalizeSaleUseCase: FinalizeSaleUseCase,
    private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PosUiState())
    val uiState: StateFlow<PosUiState> = _uiState.asStateFlow()

    init {
        observeProductsUseCase().onEach { products ->
            _uiState.value = _uiState.value.copy(products = products)
        }.launchIn(viewModelScope)
        observeCustomersUseCase().onEach { customers ->
            _uiState.value = _uiState.value.copy(customers = customers)
        }.launchIn(viewModelScope)
        observeActiveUserUseCase().onEach { user ->
            _uiState.value = _uiState.value.copy(activeUser = user)
        }.launchIn(viewModelScope)
    }

    fun onSearchChanged(value: String) {
        _uiState.value = _uiState.value.copy(searchQuery = value)
    }

    fun addProduct(product: Product) {
        val cart = _uiState.value.cart.toMutableList()
        val index = cart.indexOfFirst { it.product.id == product.id }
        if (index >= 0) {
            val current = cart[index]
            cart[index] = current.copy(quantity = current.quantity + 1)
        } else {
            cart.add(CartItem(product = product, quantity = 1.0))
        }
        _uiState.value = _uiState.value.copy(cart = cart)
        recalcTotals()
    }

    fun addByBarcode(barcode: String) {
        viewModelScope.launch {
            val product = getProductByBarcodeUseCase(barcode)
            product?.let { addProduct(it) }
        }
    }

    fun updateQuantity(productId: Long, quantity: Double) {
        val cart = _uiState.value.cart.toMutableList()
        val index = cart.indexOfFirst { it.product.id == productId }
        if (index >= 0) {
            val sanitized = quantity.coerceAtLeast(0.0)
            if (sanitized == 0.0) {
                cart.removeAt(index)
            } else {
                cart[index] = cart[index].copy(quantity = sanitized)
            }
            _uiState.value = _uiState.value.copy(cart = cart)
            recalcTotals()
        }
    }

    fun selectCustomer(customer: Customer?) {
        _uiState.value = _uiState.value.copy(selectedCustomer = customer)
    }

    fun useLoyalty(points: Double) {
        _uiState.value = _uiState.value.copy(loyaltyToSpend = points)
        recalcTotals()
    }

    fun finalizeSale() {
        val user = _uiState.value.activeUser ?: return
        val cartItems = _uiState.value.cart
        if (cartItems.isEmpty()) return
        val payments = SalePayments(
            paidCash = _uiState.value.totalDue,
            paidCard = 0.0,
            paidOther = 0.0,
            manualDiscount = 0.0,
            loyaltyEarnRate = 0.02
        )
        viewModelScope.launch {
            finalizeSaleUseCase(
                cartItems = cartItems.map { it.product to it.quantity },
                userId = user.id,
                customerId = _uiState.value.selectedCustomer?.id,
                loyaltyToSpend = _uiState.value.loyaltyToSpend,
                payments = payments
            )
            _uiState.value = PosUiState(activeUser = user, products = _uiState.value.products, customers = _uiState.value.customers)
        }
    }

    private fun recalcTotals() {
        val subtotal = _uiState.value.cart.sumOf { it.product.sellingPrice * it.quantity }
        val tax = _uiState.value.cart.sumOf { it.product.taxRatePercent / 100 * it.product.sellingPrice * it.quantity }
        val total = subtotal + tax - _uiState.value.loyaltyToSpend
        _uiState.value = _uiState.value.copy(subtotal = subtotal, tax = tax, totalDue = total.coerceAtLeast(0.0))
    }
}

data class PosUiState(
    val searchQuery: String = "",
    val products: List<Product> = emptyList(),
    val customers: List<Customer> = emptyList(),
    val cart: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val totalDue: Double = 0.0,
    val selectedCustomer: Customer? = null,
    val loyaltyToSpend: Double = 0.0,
    val activeUser: User? = null
)

data class CartItem(
    val product: Product,
    val quantity: Double
)
