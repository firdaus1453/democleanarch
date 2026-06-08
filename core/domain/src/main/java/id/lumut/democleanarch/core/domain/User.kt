package id.lumut.democleanarch.core.domain

/**
 * User model — SHARED di :core:domain.
 *
 * Awalnya ada di :auth:domain, tapi karena :home juga butuh User,
 * kita pindahkan ke :core:domain agar bisa diakses semua feature.
 *
 * Ini sesuai pattern Philipp Lackner di Runique:
 *   :core:domain berisi model/util yang dipakai lintas feature.
 */
data class User(
    val id: String,
    val email: String,
    val name: String
)
