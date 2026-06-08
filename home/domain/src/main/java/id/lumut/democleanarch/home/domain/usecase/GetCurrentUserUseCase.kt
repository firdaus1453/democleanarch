package id.lumut.democleanarch.home.domain.usecase

import id.lumut.democleanarch.core.domain.AppResult
import id.lumut.democleanarch.core.domain.User
import id.lumut.democleanarch.home.domain.repository.UserRepository

/**
 * Use case: ambil data user yang sedang login.
 * Menggunakan UserRepository dari :home:domain (bukan dari :auth).
 */
class GetCurrentUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): AppResult<User> {
        return userRepository.getCurrentUser()
    }
}
