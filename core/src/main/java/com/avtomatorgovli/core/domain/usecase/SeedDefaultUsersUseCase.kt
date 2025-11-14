package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.repository.AuthRepository
import javax.inject.Inject

class SeedDefaultUsersUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.seedDefaultUsers()
}
