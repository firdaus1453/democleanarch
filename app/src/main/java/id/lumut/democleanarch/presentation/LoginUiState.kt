package id.lumut.democleanarch.presentation

import id.lumut.democleanarch.domain.model.User

/**
 * UI State untuk Login screen.
 *
 * Sealed interface digunakan agar state EXPLICIT dan EXHAUSTIVE.
 * Compose akan me-render ulang UI berdasarkan state ini.
 *
 * Presentation layer hanya tahu tentang:
 * - Domain model (User)
 * - State yang dibutuhkan untuk UI
 *
 * TIDAK TAHU tentang data layer (API, database, dll).
 */
sealed interface LoginUiState {
    /** State awal — belum ada aksi */
    data object Idle : LoginUiState

    /** Sedang proses login */
    data object Loading : LoginUiState

    /** Login berhasil — membawa data User dari domain */
    data class Success(val user: User) : LoginUiState

    /** Login gagal — membawa pesan error */
    data class Error(val message: String) : LoginUiState
}
