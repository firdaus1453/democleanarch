package id.lumut.democleanarch.domain.model

/**
 * Domain model yang merepresentasikan User.
 * Model ini TIDAK bergantung ke framework apapun (pure Kotlin).
 * Ini adalah inti dari domain layer - hanya data, tanpa logic framework.
 */
data class User(
    val id: String,
    val email: String,
    val name: String
)
