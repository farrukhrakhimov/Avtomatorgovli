package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.Customer
import com.avtomatorgovli.core.domain.model.LoyaltyTransaction
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun observeCustomers(query: String = ""): Flow<List<Customer>>
    suspend fun getCustomerByCard(cardNumber: String): Customer?
    suspend fun upsertCustomer(customer: Customer)
    fun observeLoyaltyTransactions(customerId: Long): Flow<List<LoyaltyTransaction>>
    suspend fun addLoyaltyTransaction(transaction: LoyaltyTransaction)
}
