package com.avtomatorgovli.core.domain.usecase

import com.avtomatorgovli.core.domain.model.AppSetting
import com.avtomatorgovli.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSettingUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(key: String): Flow<AppSetting?> = repository.observeSetting(key)
}
