package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.repository.SyncRepository
import javax.inject.Inject

class EnqueueFullSyncUseCase @Inject constructor(
    private val syncRepository: SyncRepository
) {
    suspend operator fun invoke() = syncRepository.enqueueFullSync()
}
