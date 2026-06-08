package id.lumut.democleanarch.home.presentation

import id.lumut.democleanarch.core.domain.User

/**
 * UI State untuk HomeScreen.
 */
data class HomeUiState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val greeting: String = "",
    val isLoggedOut: Boolean = false,
    val errorMessage: String? = null
)
