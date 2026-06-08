package id.lumut.democleanarch.core.common

/**
 * Shared Result wrapper untuk semua feature module.
 *
 * Berada di :core:common agar bisa digunakan oleh:
 * - :feature:auth:domain
 * - :feature:auth:data
 * - :feature:home:domain (jika ada feature baru)
 * - dll.
 *
 * Ini adalah contoh code yang SHARED di antara semua feature.
 * Setiap feature tidak perlu membuat wrapper sendiri.
 */
sealed interface DataError {
    /** Error terkait autentikasi */
    data class Authentication(val message: String) : DataError

    /** Error terkait jaringan */
    data class Network(val message: String) : DataError

    /** Error tidak diketahui */
    data class Unknown(val message: String) : DataError
}

/**
 * Type-safe result wrapper.
 * Lebih expressive daripada kotlin.Result karena error type-nya explicit.
 */
sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val error: DataError) : AppResult<Nothing>
}
