package id.lumut.democleanarch.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.lumut.democleanarch.core.domain.AppResult
import id.lumut.democleanarch.core.domain.DataError
import id.lumut.democleanarch.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * LoginViewModel — di :feature:auth:presentation module.
 *
 * Menggunakan LoginUseCase dari :feature:auth:domain.
 * Handle AppResult dari :core:common.
 * TIDAK tahu tentang :feature:auth:data.
 */
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            when (val result = loginUseCase(email.value, password.value)) {
                is AppResult.Success -> {
                    _uiState.value = LoginUiState.Success(result.data)
                }
                is AppResult.Error -> {
                    val message = when (val error = result.error) {
                        is DataError.Authentication -> error.message
                        is DataError.Network -> error.message
                        is DataError.Unknown -> error.message
                    }
                    _uiState.value = LoginUiState.Error(message)
                }
            }
        }
    }
}
