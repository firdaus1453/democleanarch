package id.lumut.democleanarch.domain.repository

import id.lumut.democleanarch.domain.model.User

/**
 * Contract/interface untuk AuthRepository.
 *
 * Berada di :domain module — module lain yang butuh
 * harus menambahkan dependency ke :domain.
 *
 * :data module akan IMPLEMENT interface ini.
 * :app module akan MENGGUNAKAN interface ini (via UseCase).
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
}
