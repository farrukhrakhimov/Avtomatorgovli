package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Supplier
import com.avtomatorgovli.core.domain.repository.SupplierRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSuppliersUseCase @Inject constructor(
    private val repository: SupplierRepository
) {
    operator fun invoke(query: String = ""): Flow<List<Supplier>> = repository.observeSuppliers(query)
}
