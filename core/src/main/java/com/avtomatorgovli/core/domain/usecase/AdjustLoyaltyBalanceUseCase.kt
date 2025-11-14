package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.LoyaltyTransaction
import com.avtomatorgovli.core.domain.repository.CustomerRepository
import javax.inject.Inject

class AdjustLoyaltyBalanceUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(transaction: LoyaltyTransaction) {
        repository.addLoyaltyTransaction(transaction)
    }
}
