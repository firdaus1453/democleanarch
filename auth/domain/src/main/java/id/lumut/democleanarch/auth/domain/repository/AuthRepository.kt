package id.lumut.democleanarch.auth.domain.repository

import id.lumut.democleanarch.core.domain.AppResult
import id.lumut.democleanarch.core.domain.User

/**
 * Repository contract KHUSUS feature Auth.
 *
 * Menggunakan AppResult dari :core:common
 * sebagai shared result wrapper.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): AppResult<User>
}
