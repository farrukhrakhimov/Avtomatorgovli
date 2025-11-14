package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.User
import com.avtomatorgovli.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        val userResult = authRepository.authenticate(username, password)
        userResult.getOrNull()?.let { authRepository.setActiveUser(it) }
        return userResult
    }
}
