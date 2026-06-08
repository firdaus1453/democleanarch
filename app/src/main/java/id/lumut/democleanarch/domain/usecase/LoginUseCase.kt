package id.lumut.democleanarch.domain.usecase

import id.lumut.democleanarch.domain.model.User
import id.lumut.democleanarch.domain.repository.AuthRepository

/**
 * Use Case untuk Login.
 *
 * Use Case berisi BUSINESS LOGIC yang spesifik untuk satu aksi.
 * Di sini kita bisa menambahkan validasi, transformasi, atau
 * menggabungkan beberapa repository.
 *
 * Use Case hanya bergantung ke interface (AuthRepository),
 * BUKAN ke implementasi konkrit — sehingga mudah di-test.
 *
 * @param authRepository — di-inject melalui constructor (manual DI)
 */
class LoginUseCase(
    private val authRepository: AuthRepository
) {
    /**
     * Eksekusi login dengan validasi input.
     *
     * Validasi email & password dilakukan DI SINI (domain),
     * bukan di presentation layer — karena ini adalah business rule.
     */
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Business rule: validasi input
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
