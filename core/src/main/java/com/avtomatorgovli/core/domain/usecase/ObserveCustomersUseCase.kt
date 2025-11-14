package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Customer
import com.avtomatorgovli.core.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCustomersUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    operator fun invoke(query: String = ""): Flow<List<Customer>> = repository.observeCustomers(query)
}
