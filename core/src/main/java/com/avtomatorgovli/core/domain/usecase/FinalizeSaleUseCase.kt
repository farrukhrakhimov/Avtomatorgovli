package com.avtomatorgovli.core.domain.usecase

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
import java.util.UUID
import javax.inject.Inject

class FinalizeSaleUseCase @Inject constructor(
    private val saleRepository: SaleRepository,
    private val stockRepository: StockRepository,
    private val productRepository: ProductRepository,
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(
        cartItems: List<Pair<Product, Double>>,
        userId: Long,
        customerId: Long?,
        loyaltyToSpend: Double,
        payments: SalePayments
    ): Sale {
        if (cartItems.isEmpty()) {
            throw SaleValidationException("Пустая корзина не может быть оплачена")
        }
        if (loyaltyToSpend < 0) {
            throw SaleValidationException("Нельзя использовать отрицательное количество бонусов")
        }
        val validatedItems = cartItems.map { (product, quantity) ->
            if (quantity <= 0) {
                throw SaleValidationException("Количество должно быть больше нуля")
            }
            if (!product.isActive) {
                throw SaleValidationException("Товар ${product.name} деактивирован")
            }
            if (product.currentStock < quantity) {
                throw SaleValidationException("Недостаточно остатка для ${product.name}")
            }
            product to quantity
        }
        val receiptNumber = saleRepository.nextReceiptNumber()
        val localUuid = UUID.randomUUID().toString()
        val timestamp = System.currentTimeMillis()
        val items = validatedItems.mapIndexed { index, (product, quantity) ->
            val lineWithoutTax = quantity * product.sellingPrice
            val lineTax = lineWithoutTax * product.taxRatePercent / 100.0
            SaleItem(
                id = index.toLong(),
                saleId = 0L,
                productId = product.id,
                quantity = quantity,
                unitPrice = product.sellingPrice,
                discountPercent = 0.0,
                taxRatePercent = product.taxRatePercent,
                lineTotalWithoutTax = lineWithoutTax,
                lineTax = lineTax,
                lineTotalWithTax = lineWithoutTax + lineTax
            )
        }
        val totalsWithoutTax = items.sumOf { it.lineTotalWithoutTax }
        val totalsTax = items.sumOf { it.lineTax }
        if (payments.manualDiscount < 0) {
            throw SaleValidationException("Скидка не может быть отрицательной")
        }
        val grossTotal = totalsWithoutTax + totalsTax
        if (payments.manualDiscount > grossTotal) {
            throw SaleValidationException("Скидка превышает сумму чека")
        }
        val totalsWithTax = grossTotal - payments.manualDiscount
        if (loyaltyToSpend > totalsWithTax) {
            throw SaleValidationException("Количество бонусов превышает сумму к оплате")
        }
        val totalDue = totalsWithTax - loyaltyToSpend
        payments.validateAgainst(totalDue)
        val loyaltyEarned = totalsWithTax * payments.loyaltyEarnRate
        val sale = Sale(
            id = 0L,
            localUuid = localUuid,
            receiptNumber = receiptNumber,
            dateTime = timestamp,
            customerId = customerId,
            userId = userId,
            totalWithoutTax = totalsWithoutTax,
            totalTax = totalsTax,
            totalWithTax = totalsWithTax,
            discountAmount = payments.manualDiscount,
            paidCash = payments.paidCash,
            paidCard = payments.paidCard,
            paidOther = payments.paidOther,
            changeGiven = payments.calculateChange(totalDue),
            loyaltyPointsUsed = loyaltyToSpend,
            loyaltyPointsEarned = loyaltyEarned,
            isRefund = false,
            syncStatus = SyncStatus.PENDING,
            items = items
        )
        saleRepository.finalizeSale(sale)
        validatedItems.forEach { (product, quantity) ->
            val movement = StockMovement(
                id = 0L,
                productId = product.id,
                dateTime = timestamp,
                quantityChange = -quantity,
                sourceType = "SALE",
                sourceId = 0L,
                notes = receiptNumber
            )
            stockRepository.registerMovement(movement)
            productRepository.upsertProduct(product.copy(currentStock = product.currentStock - quantity))
        }
        if (customerId != null) {
            val loyaltyTransaction = LoyaltyTransaction(
                id = 0L,
                customerId = customerId,
                dateTime = timestamp,
                pointsChange = loyaltyEarned - loyaltyToSpend,
                sourceType = "SALE",
                sourceId = 0L,
                notes = receiptNumber
            )
            customerRepository.addLoyaltyTransaction(loyaltyTransaction)
        }
        return sale
    }
}

data class SalePayments(
    val paidCash: Double,
    val paidCard: Double,
    val paidOther: Double,
    val manualDiscount: Double,
    val loyaltyEarnRate: Double
) {
    private val totalTendered = paidCash + paidCard + paidOther

    fun calculateChange(totalDue: Double): Double = (totalTendered - totalDue).coerceAtLeast(0.0)

    fun validateAgainst(totalDue: Double) {
        if (totalTendered + 0.001 < totalDue) {
            throw SaleValidationException("Недостаточно средств для оплаты")
        }
    }
}
