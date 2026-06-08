package id.lumut.democleanarch.core.domain

/**
 * Shared Result wrapper — di :core:domain (Philipp Lackner style).
 *
 * Sama seperti Runique, shared domain code ada di :core:domain.
 * Semua feature module boleh depend ke :core:domain.
 */
sealed interface DataError {
    data class Authentication(val message: String) : DataError
    data class Network(val message: String) : DataError
    data class Unknown(val message: String) : DataError
}

sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val error: DataError) : AppResult<Nothing>
}
