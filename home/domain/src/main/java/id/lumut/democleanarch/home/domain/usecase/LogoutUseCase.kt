package id.lumut.democleanarch.home.domain.usecase

import id.lumut.democleanarch.home.domain.repository.UserRepository

/**
 * Use case: logout user.
 */
class LogoutUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        userRepository.logout()
    }
}
