package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.AppSetting
import com.avtomatorgovli.core.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveSettingUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(setting: AppSetting) = repository.setSetting(setting)
}
