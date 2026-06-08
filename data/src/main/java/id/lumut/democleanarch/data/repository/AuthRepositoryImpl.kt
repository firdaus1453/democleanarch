package id.lumut.democleanarch.data.repository

import id.lumut.democleanarch.domain.model.User
import id.lumut.democleanarch.domain.repository.AuthRepository
import kotlinx.coroutines.delay

/**
 * Implementasi KONKRIT dari AuthRepository.
 *
 * Sekarang berada di MODULE :data yang TERPISAH dari :domain dan :app.
 *
 * Dependency graph:
 *   :data → :domain  (data bergantung ke domain)
 *   :app → :domain   (app bergantung ke domain)
 *   :app → :data     (app butuh implementation untuk DI)
 *
 * Perhatikan: :domain TIDAK bergantung ke :data
 * → Dependency Inversion Principle ter-enforce oleh Gradle!
 */
class AuthRepositoryImpl : AuthRepository {

    private val dummyUsers = listOf(
        User(id = "1", email = "admin@test.com", name = "Admin User"),
        User(id = "2", email = "user@test.com", name = "Regular User")
    )

    override suspend fun login(email: String, password: String): Result<User> {
        // Simulasi network delay
        delay(1000)

        val user = dummyUsers.find { it.email == email }

        return if (user != null && password == "password123") {
            Result.success(user)
        } else {
            Result.failure(Exception("Email atau password salah"))
        }
    }
}
