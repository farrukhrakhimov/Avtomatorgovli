package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Customer
import com.avtomatorgovli.core.domain.model.LoyaltyTransaction
import com.avtomatorgovli.core.domain.model.Product
import com.avtomatorgovli.core.domain.model.Sale
import com.avtomatorgovli.core.domain.model.SaleItem
import com.avtomatorgovli.core.domain.model.StockMovement
import com.avtomatorgovli.core.domain.model.SyncStatus
import com.avtomatorgovli.core.domain.repository.CustomerRepository
import com.avtomatorgovli.core.domain.repository.ProductRepository
import com.avtomatorgovli.core.domain.repository.SaleRepository
import com.avtomatorgovli.core.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FinalizeSaleUseCaseTest {
    @Test
    fun `finalize sale updates totals`() = runTest {
        val product = Product(
            id = 1L,
            name = "Товар",
            sku = null,
            barcode = null,
            categoryId = 0,
            unitOfMeasure = "шт",
            purchasePrice = 50.0,
            sellingPrice = 100.0,
            taxRatePercent = 12.0,
            isActive = true,
            minStockLevel = 1.0,
            currentStock = 10.0,
            createdAt = 0L,
            updatedAt = 0L,
            isDeleted = false
        )
        val saleRepo = InMemorySaleRepository()
        val productRepo = InMemoryProductRepository(product)
        val customerRepo = InMemoryCustomerRepository()
        val stockRepo = InMemoryStockRepository()
        val useCase = FinalizeSaleUseCase(saleRepo, stockRepo, productRepo, customerRepo)

        val sale = useCase(
            cartItems = listOf(product to 2.0),
            userId = 1L,
            customerId = null,
            loyaltyToSpend = 0.0,
            payments = SalePayments(200.0, 0.0, 0.0, 0.0, 0.02)
        )

        assertEquals(224.0, sale.totalWithTax, 0.01)
        assertEquals(-2.0, stockRepo.movements.first().quantityChange, 0.0)
        assertEquals(8.0, productRepo.lastSavedProduct.currentStock, 0.0)
    }
}

private class InMemorySaleRepository : SaleRepository {
    val sales = mutableListOf<Sale>()
    override suspend fun finalizeSale(sale: Sale) { sales.add(sale.copy(id = sales.size + 1L)) }
    override fun observeSales(from: Long, to: Long): Flow<List<Sale>> = MutableStateFlow(sales)
    override suspend fun nextReceiptNumber(): String = "TEST-${sales.size + 1}"
}

private class InMemoryProductRepository(private var product: Product) : ProductRepository {
    var lastSavedProduct: Product = product
    override fun observeProducts(query: String, categoryId: Long?, onlyActive: Boolean): Flow<List<Product>> = MutableStateFlow(listOf(product))
    override fun observeLowStockProducts(): Flow<List<Product>> = MutableStateFlow(emptyList())
    override suspend fun getProductByBarcode(barcode: String): Product? = product
    override suspend fun getProductById(id: Long): Product? = product
    override suspend fun upsertProduct(product: Product) {
        this.product = product
        this.lastSavedProduct = product
    }
    override suspend fun upsertCategory(category: com.avtomatorgovli.core.domain.model.ProductCategory) {}
    override fun observeCategories(): Flow<List<com.avtomatorgovli.core.domain.model.ProductCategory>> = emptyFlow()
}

private class InMemoryCustomerRepository : CustomerRepository {
    override fun observeCustomers(query: String): Flow<List<Customer>> = MutableStateFlow(emptyList())
    override suspend fun getCustomerByCard(cardNumber: String): Customer? = null
    override suspend fun upsertCustomer(customer: Customer) {}
    override fun observeLoyaltyTransactions(customerId: Long): Flow<List<LoyaltyTransaction>> = MutableStateFlow(emptyList())
    override suspend fun addLoyaltyTransaction(transaction: LoyaltyTransaction) {}
}

private class InMemoryStockRepository : StockRepository {
    val movements = mutableListOf<StockMovement>()
    override suspend fun registerMovement(movement: StockMovement) { movements.add(movement) }
    override fun observeMovements(productId: Long): Flow<List<StockMovement>> = MutableStateFlow(movements)
}
