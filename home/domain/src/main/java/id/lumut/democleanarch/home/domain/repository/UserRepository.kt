package id.lumut.democleanarch.home.domain.repository

import id.lumut.democleanarch.core.domain.AppResult
import id.lumut.democleanarch.core.domain.User

/**
 * Repository interface untuk Home feature.
 *
 * PENTING (Philipp Lackner rule):
 *   - :home:domain TIDAK depend ke :auth:domain
 *   - User model datang dari :core:domain (shared)
 *   - Interface ini di-implement di :home:data
 */
interface UserRepository {
    suspend fun getCurrentUser(): AppResult<User>
    suspend fun logout()
}
