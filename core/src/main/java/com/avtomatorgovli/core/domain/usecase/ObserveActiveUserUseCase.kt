package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.User
import com.avtomatorgovli.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveActiveUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User?> = authRepository.observeActiveUser()
}
