package id.lumut.democleanarch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.lumut.democleanarch.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel untuk Login screen.
 *
 * ViewModel adalah bagian dari PRESENTATION layer:
 * - Menerima input dari UI (email, password, klik login)
 * - Memanggil USE CASE dari domain layer
 * - Mengubah state UI berdasarkan hasil
 *
 * ViewModel TIDAK TAHU tentang:
 * - Data layer (API, database)
 * - Implementasi repository
 *
 * ViewModel HANYA TAHU tentang:
 * - Use Case (dari domain layer)
 * - UI State (dari presentation layer)
 *
 * @param loginUseCase — di-inject melalui constructor (manual DI)
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
        // Reset error state saat user mulai mengetik
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

            loginUseCase(email.value, password.value)
                .onSuccess { user ->
                    _uiState.value = LoginUiState.Success(user)
                }
                .onFailure { error ->
                    _uiState.value = LoginUiState.Error(
                        error.message ?: "Terjadi kesalahan"
                    )
                }
        }
    }
}
