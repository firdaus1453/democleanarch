package id.lumut.democleanarch.feature.auth.domain.usecase

import id.lumut.democleanarch.core.common.AppResult
import id.lumut.democleanarch.core.common.DataError
import id.lumut.democleanarch.feature.auth.domain.model.User
import id.lumut.democleanarch.feature.auth.domain.repository.AuthRepository

/**
 * LoginUseCase — KHUSUS untuk feature Auth.
 *
 * Perubahan di UseCase ini HANYA akan me-rebuild:
 * - :feature:auth:domain
 * - :feature:auth:presentation (karena depends on domain)
 *
 * Module lain seperti :feature:home TIDAK akan ter-rebuild!
 * Ini adalah keuntungan utama Feature Layered Base vs 3 Layer biasa.
 */
class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AppResult<User> {
        if (email.isBlank()) {
            return AppResult.Error(
                DataError.Authentication("Email tidak boleh kosong")
            )
        }
        if (password.isBlank()) {
            return AppResult.Error(
                DataError.Authentication("Password tidak boleh kosong")
            )
        }
        if (!email.contains("@")) {
            return AppResult.Error(
                DataError.Authentication("Format email tidak valid")
            )
        }

        return authRepository.login(email, password)
    }
}
