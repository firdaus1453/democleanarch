package id.lumut.democleanarch.domain.usecase

import id.lumut.democleanarch.domain.model.User
import id.lumut.democleanarch.domain.repository.AuthRepository

/**
 * Use Case untuk Login — berisi business logic.
 *
 * Sekarang di module :domain yang terpisah.
 * Gradle ENFORCE bahwa UseCase tidak bisa mengakses:
 * - Compose UI (di :app)
 * - Repository implementation (di :data)
 *
 * Ini menyelesaikan masalah single module yang
 * "mudah melanggar aturan karena tidak ada paksaan strict rules"
 */
class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email tidak boleh kosong"))
        }
        if (password.isBlank()) {
            return Result.failure(IllegalArgumentException("Password tidak boleh kosong"))
        }
        if (!email.contains("@")) {
            return Result.failure(IllegalArgumentException("Format email tidak valid"))
        }

        return authRepository.login(email, password)
    }
}
