package id.lumut.democleanarch.feature.auth.presentation

import id.lumut.democleanarch.feature.auth.domain.model.User

/**
 * UI State untuk Login — di :feature:auth:presentation module.
 */
sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val user: User) : LoginUiState
    data class Error(val message: String) : LoginUiState
}
