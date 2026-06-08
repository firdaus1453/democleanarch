package id.lumut.democleanarch.data.repository

import id.lumut.democleanarch.domain.model.User
import id.lumut.democleanarch.domain.repository.AuthRepository
import kotlinx.coroutines.delay

/**
 * Implementasi KONKRIT dari AuthRepository.
 *
 * Di sini kita menggunakan DUMMY DATA untuk demo.
 * Dalam real app, ini bisa diganti dengan:
 * - API call (Retrofit/Ktor)
 * - Local database (Room)
 * - Kombinasi keduanya (cache strategy)
 *
 * Yang penting: Presentation layer TIDAK TAHU implementasi ini.
 * Presentation hanya tahu interface AuthRepository dari domain layer.
 *
 * Data layer BERGANTUNG ke Domain layer (implements interface dari domain).
 */
class AuthRepositoryImpl : AuthRepository {

    // Dummy data — simulasi database/API
    private val dummyUsers = listOf(
        User(id = "1", email = "admin@test.com", name = "Admin User"),
        User(id = "2", email = "user@test.com", name = "Regular User")
    )

    override suspend fun login(email: String, password: String): Result<User> {
        // Simulasi network delay
        delay(1000)

        // Dummy login logic
        val user = dummyUsers.find { it.email == email }

        return if (user != null && password == "password123") {
            Result.success(user)
        } else {
            Result.failure(Exception("Email atau password salah"))
        }
    }
}
