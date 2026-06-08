package id.lumut.democleanarch.home.data.repository

import id.lumut.democleanarch.core.domain.AppResult
import id.lumut.democleanarch.core.domain.User
import id.lumut.democleanarch.home.domain.repository.UserRepository
import kotlinx.coroutines.delay

/**
 * Implementasi UserRepository — dummy data tanpa internet.
 *
 * Menggunakan data yang sama (User dari :core:domain),
 * tapi TIDAK depend ke :auth module apapun.
 *
 * Di real app, ini bisa baca dari local DB / SharedPrefs
 * yang di-write oleh :auth:data setelah login berhasil.
 */
class UserRepositoryImpl : UserRepository {

    // Simulasi cached user setelah login
    private var cachedUser: User? = User(
        id = "1",
        email = "admin@demo.com",
        name = "Admin Demo"
    )

    override suspend fun getCurrentUser(): AppResult<User> {
        delay(300) // Simulasi load dari local storage
        val user = cachedUser
        return if (user != null) {
            AppResult.Success(user)
        } else {
            AppResult.Error(
                id.lumut.democleanarch.core.domain.DataError.Authentication("User not logged in")
            )
        }
    }

    override suspend fun logout() {
        delay(200)
        cachedUser = null
    }
}
