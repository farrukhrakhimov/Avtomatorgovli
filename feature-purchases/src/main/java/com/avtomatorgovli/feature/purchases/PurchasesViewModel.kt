package com.avtomatorgovli.feature.purchases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.Purchase
import com.avtomatorgovli.core.domain.model.PurchaseItem
import com.avtomatorgovli.core.domain.model.SyncStatus
import com.avtomatorgovli.core.domain.usecase.SavePurchaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class PurchasesViewModel @Inject constructor(
    private val savePurchaseUseCase: SavePurchaseUseCase
) : ViewModel() {
    fun createDemoPurchase(productId: Long, quantity: Double, price: Double, supplierId: Long) {
        val purchase = Purchase(
            id = 0L,
            localUuid = UUID.randomUUID().toString(),
            documentNumber = "PR-${UUID.randomUUID().toString().take(4)}",
            dateTime = System.currentTimeMillis(),
            supplierId = supplierId,
            totalAmount = quantity * price,
            notes = null,
            syncStatus = SyncStatus.PENDING,
            items = listOf(
                PurchaseItem(
                    id = 0L,
                    purchaseId = 0L,
                    productId = productId,
                    quantity = quantity,
                    purchasePrice = price,
                    lineTotal = quantity * price
                )
            )
        )
        viewModelScope.launch { savePurchaseUseCase(purchase) }
    }
}
