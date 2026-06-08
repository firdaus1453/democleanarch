package id.lumut.democleanarch.domain.model

/**
 * Domain model yang merepresentasikan User.
 * Pure Kotlin — tidak ada dependency framework.
 *
 * Sekarang berada di MODULE TERPISAH (:domain),
 * sehingga Gradle ENFORCE bahwa model ini tidak bisa
 * mengakses code dari :data atau :app.
 */
data class User(
    val id: String,
    val email: String,
    val name: String
)
