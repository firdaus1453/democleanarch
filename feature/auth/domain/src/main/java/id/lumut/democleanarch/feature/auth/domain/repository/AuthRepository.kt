package id.lumut.democleanarch.feature.auth.domain.repository

import id.lumut.democleanarch.core.common.AppResult
import id.lumut.democleanarch.feature.auth.domain.model.User

/**
 * Repository contract KHUSUS feature Auth.
 *
 * Menggunakan AppResult dari :core:common
 * sebagai shared result wrapper.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): AppResult<User>
}
