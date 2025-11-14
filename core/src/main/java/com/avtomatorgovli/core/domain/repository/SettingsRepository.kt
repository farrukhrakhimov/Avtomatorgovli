package com.avtomatorgovli.core.domain.repository

import com.avtomatorgovli.core.domain.model.AppSetting
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSetting(key: String): Flow<AppSetting?>
    suspend fun setSetting(setting: AppSetting)
    suspend fun getSetting(key: String, defaultValue: String = ""): String
}
