package id.lumut.democleanarch.feature.auth.data.repository

import id.lumut.democleanarch.core.common.AppResult
import id.lumut.democleanarch.core.common.DataError
import id.lumut.democleanarch.feature.auth.domain.model.User
import id.lumut.democleanarch.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

/**
 * Implementasi AuthRepository KHUSUS feature Auth.
 *
 * Dependency:
 *   :feature:auth:data → :feature:auth:domain (implements interface)
 *   :feature:auth:data → :core:common (AppResult, DataError)
 *
 * TIDAK bergantung ke feature lain!
 * Perubahan di :feature:home:data TIDAK akan mempengaruhi module ini.
 */
class AuthRepositoryImpl : AuthRepository {

    private val dummyUsers = listOf(
        User(id = "1", email = "admin@test.com", name = "Admin User"),
        User(id = "2", email = "user@test.com", name = "Regular User")
    )

    override suspend fun login(email: String, password: String): AppResult<User> {
        // Simulasi network delay
        delay(1000)

        val user = dummyUsers.find { it.email == email }

        return if (user != null && password == "password123") {
            AppResult.Success(user)
        } else {
            AppResult.Error(
                DataError.Authentication("Email atau password salah")
            )
        }
    }
}
