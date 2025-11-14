package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.User
import com.avtomatorgovli.core.domain.model.UserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun authenticate(username: String, password: String): Result<User>
    suspend fun seedDefaultUsers()
    fun observeActiveUser(): Flow<User?>
    suspend fun logout()
    suspend fun setActiveUser(user: User)
    suspend fun createUser(
        username: String,
        password: String,
        displayName: String,
        role: UserRole,
        isActive: Boolean
    ): User
}
