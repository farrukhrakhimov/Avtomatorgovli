package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.data.datastore.SessionDataStore
import com.avtomatorgovli.core.data.local.dao.UserDao
import com.avtomatorgovli.core.data.local.entity.UserEntity
import com.avtomatorgovli.core.data.mapper.toDomain
import com.avtomatorgovli.core.data.security.PasswordHasher
import com.avtomatorgovli.core.domain.model.User
import com.avtomatorgovli.core.domain.model.UserRole
import com.avtomatorgovli.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val sessionDataStore: SessionDataStore,
    private val passwordHasher: PasswordHasher
) : AuthRepository {
    override suspend fun authenticate(username: String, password: String): Result<User> {
        val userEntity = userDao.findByUsername(username)
            ?: return Result.failure(IllegalArgumentException("Пользователь не найден"))
        val expectedHash = userEntity.passwordHash
        val inputHash = passwordHasher.hash(password)
        return if (expectedHash == inputHash && userEntity.isActive) {
            Result.success(userEntity.toDomain())
        } else {
            Result.failure(IllegalStateException("Неверные данные"))
        }
    }

    override suspend fun seedDefaultUsers() {
        if (userDao.findByUsername("admin") != null) return
        val now = System.currentTimeMillis()
        listOf(
            Triple("admin", "admin", UserRole.ADMIN),
            Triple("manager", "manager", UserRole.MANAGER),
            Triple("cashier", "cashier", UserRole.CASHIER)
        ).forEach { (username, password, role) ->
            val entity = UserEntity(
                id = 0L,
                username = username,
                displayName = username.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                passwordHash = passwordHasher.hash(password),
                role = role.name,
                isActive = true,
                createdAt = now,
                updatedAt = now
            )
            userDao.insert(entity)
        }
        Timber.i("Default users seeded")
    }

    override fun observeActiveUser(): Flow<User?> = sessionDataStore.activeUserId.flatMapLatest { id ->
        if (id == null) {
            flow { emit(null) }
        } else {
            flow {
                emit(userDao.findById(id)?.toDomain())
            }
        }
    }

    override suspend fun logout() {
        sessionDataStore.clear()
    }

    override suspend fun setActiveUser(user: User) {
        sessionDataStore.setActiveUser(user.id)
    }

    override suspend fun createUser(
        username: String,
        password: String,
        displayName: String,
        role: UserRole,
        isActive: Boolean
    ): User {
        val now = System.currentTimeMillis()
        val entity = UserEntity(
            id = 0L,
            username = username,
            displayName = displayName,
            passwordHash = passwordHasher.hash(password),
            role = role.name,
            isActive = isActive,
            createdAt = now,
            updatedAt = now
        )
        userDao.insert(entity)
        val stored = userDao.findByUsername(username) ?: entity
        return stored.toDomain()
    }
}
