package id.lumut.democleanarch.feature.auth.domain.model

/**
 * Domain model User — KHUSUS untuk feature Auth.
 *
 * Berbeda dengan multi module 3 layer:
 * - Di 3 layer: User ada di module :domain (shared semua feature)
 * - Di feature layered: User ada di :feature:auth:domain (khusus auth)
 *
 * Jika feature lain butuh User, bisa:
 * 1. Pindahkan ke :core:common (jika memang shared)
 * 2. Buat model sendiri di feature masing-masing
 */
data class User(
    val id: String,
    val email: String,
    val name: String
)
