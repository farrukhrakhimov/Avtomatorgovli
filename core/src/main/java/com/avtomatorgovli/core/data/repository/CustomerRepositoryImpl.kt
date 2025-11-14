package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.data.local.dao.CustomerDao
import com.avtomatorgovli.core.data.mapper.toDomain
import com.avtomatorgovli.core.data.mapper.toEntity
import com.avtomatorgovli.core.domain.model.Customer
import com.avtomatorgovli.core.domain.model.LoyaltyTransaction
import com.avtomatorgovli.core.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepositoryImpl @Inject constructor(
    private val dao: CustomerDao
) : CustomerRepository {
    override fun observeCustomers(query: String): Flow<List<Customer>> = dao.observeCustomers(query).map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getCustomerByCard(cardNumber: String): Customer? = dao.getByCard(cardNumber)?.toDomain()

    override suspend fun upsertCustomer(customer: Customer) {
        dao.upsert(customer.toEntity())
    }

    override fun observeLoyaltyTransactions(customerId: Long): Flow<List<LoyaltyTransaction>> =
        dao.observeTransactions(customerId).map { list -> list.map { it.toDomain() } }

    override suspend fun addLoyaltyTransaction(transaction: LoyaltyTransaction) {
        dao.insertTransaction(transaction.toEntity())
    }
}
