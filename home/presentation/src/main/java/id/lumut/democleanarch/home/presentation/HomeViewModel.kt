package id.lumut.democleanarch.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.lumut.democleanarch.core.domain.AppResult
import id.lumut.democleanarch.home.domain.usecase.GetCurrentUserUseCase
import id.lumut.democleanarch.home.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel untuk Home — depend ke :home:domain saja.
 * TIDAK tahu tentang :auth module.
 */
class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)
            when (val result = getCurrentUserUseCase()) {
                is AppResult.Success -> {
                    val user = result.data
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        user = user,
                        greeting = "Selamat datang, ${user.name}! 👋"
                    )
                }
                is AppResult.Error -> {
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        errorMessage = "Gagal memuat user"
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            logoutUseCase()
            _uiState.value = HomeUiState(
                isLoading = false,
                isLoggedOut = true
            )
        }
    }
}
