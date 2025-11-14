package com.avtomatorgovli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.User
import com.avtomatorgovli.core.domain.usecase.LogoutUseCase
import com.avtomatorgovli.core.domain.usecase.ObserveActiveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    observeActiveUserUseCase: ObserveActiveUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainAppState())
    val state: StateFlow<MainAppState> = _state.asStateFlow()

    init {
        observeActiveUserUseCase().onEach { user ->
            _state.value = _state.value.copy(currentUser = user)
        }.launchIn(viewModelScope)
    }

    fun logout() {
        viewModelScope.launch { logoutUseCase() }
    }
}

data class MainAppState(val currentUser: User? = null)
