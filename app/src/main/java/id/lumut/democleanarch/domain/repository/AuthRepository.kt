package id.lumut.democleanarch.domain.repository

import id.lumut.democleanarch.domain.model.User

/**
 * Contract/interface untuk AuthRepository.
 *
 * Interface ini ada di DOMAIN layer — artinya domain TIDAK TAHU
 * implementasinya seperti apa (apakah dari API, database, atau dummy).
 *
 * Ini adalah penerapan Dependency Inversion Principle:
 * - Domain mendefinisikan CONTRACT (apa yang dibutuhkan)
 * - Data layer yang MENYEDIAKAN implementasinya
 */
interface AuthRepository {
    /**
     * Melakukan login dengan email dan password.
     * @return Result<User> — success dengan User, atau failure dengan exception
     */
    suspend fun login(email: String, password: String): Result<User>
}
