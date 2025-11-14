package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.Sale
import com.avtomatorgovli.core.domain.repository.SaleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSalesReportUseCase @Inject constructor(
    private val repository: SaleRepository
) {
    operator fun invoke(from: Long, to: Long): Flow<List<Sale>> = repository.observeSales(from, to)
}
