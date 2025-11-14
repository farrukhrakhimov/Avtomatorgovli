package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Purchase
import com.avtomatorgovli.core.domain.repository.PurchaseRepository
import javax.inject.Inject

class SavePurchaseUseCase @Inject constructor(
    private val repository: PurchaseRepository
) {
    suspend operator fun invoke(purchase: Purchase) = repository.savePurchase(purchase)
}
