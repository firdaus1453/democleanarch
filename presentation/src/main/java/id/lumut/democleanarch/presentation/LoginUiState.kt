package id.lumut.democleanarch.presentation

import id.lumut.democleanarch.domain.model.User

/**
 * UI State untuk Login screen.
 *
 * Import User dari :domain module — presentation bergantung ke domain.
 */
sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val user: User) : LoginUiState
    data class Error(val message: String) : LoginUiState
}
