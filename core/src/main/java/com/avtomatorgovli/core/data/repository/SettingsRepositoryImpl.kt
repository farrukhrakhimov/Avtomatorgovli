package com.avtomatorgovli.core.data.repository

import com.avtomatorgovli.core.data.local.dao.SettingsDao
import com.avtomatorgovli.core.data.mapper.toDomain
import com.avtomatorgovli.core.data.mapper.toEntity
import com.avtomatorgovli.core.domain.model.AppSetting
import com.avtomatorgovli.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val dao: SettingsDao
) : SettingsRepository {
    override fun observeSetting(key: String): Flow<AppSetting?> = dao.observeSetting(key).map { it?.toDomain() }

    override suspend fun setSetting(setting: AppSetting) {
        dao.upsert(setting.toEntity())
    }

    override suspend fun getSetting(key: String, defaultValue: String): String {
        return dao.getValue(key) ?: defaultValue
    }
}
