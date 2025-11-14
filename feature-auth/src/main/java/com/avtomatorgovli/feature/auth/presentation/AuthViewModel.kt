package com.avtomatorgovli.feature.auth.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avtomatorgovli.core.domain.model.UserRole
import com.avtomatorgovli.core.domain.usecase.AuthenticateUserUseCase
import com.avtomatorgovli.core.domain.usecase.SeedDefaultUsersUseCase
import com.avtomatorgovli.feature.auth.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val seedDefaultUsersUseCase: SeedDefaultUsersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _events = Channel<AuthEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch { seedDefaultUsersUseCase() }
    }

    fun onUsernameChanged(value: String) {
        _uiState.value = _uiState.value.copy(username = value)
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun login() {
        val username = _uiState.value.username.trim()
        val password = _uiState.value.password
        if (username.isBlank() || password.isBlank()) {
            viewModelScope.launch { _events.send(AuthEvent.Error(R.string.auth_error_empty)) }
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = authenticateUserUseCase(username, password)
            result.fold(
                onSuccess = { user ->
                    _events.send(AuthEvent.Success(user.role))
                    _uiState.value = AuthUiState()
                },
                onFailure = {
                    _events.send(AuthEvent.Error(R.string.auth_error_wrong))
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            )
        }
    }
}

data class AuthUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)

sealed interface AuthEvent {
    data class Success(val role: UserRole) : AuthEvent
    data class Error(@StringRes val messageRes: Int) : AuthEvent
}
