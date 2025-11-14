package com.avtomatorgovli.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.AppSetting
import com.avtomatorgovli.core.domain.usecase.ObserveSettingUseCase
import com.avtomatorgovli.core.domain.usecase.SaveSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeSettingUseCase: ObserveSettingUseCase,
    private val saveSettingUseCase: SaveSettingUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    init {
        observeSettingUseCase(KEY_STORE_NAME).onEach { setting ->
            _state.value = _state.value.copy(storeName = setting?.value ?: "")
        }.launchIn(viewModelScope)
    }

    fun updateStoreName(value: String) {
        _state.value = _state.value.copy(storeName = value)
    }

    fun save() {
        viewModelScope.launch {
            saveSettingUseCase(AppSetting(KEY_STORE_NAME, _state.value.storeName))
        }
    }

    companion object {
        private const val KEY_STORE_NAME = "store_name"
    }
}

data class SettingsUiState(val storeName: String = "")
