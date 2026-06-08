package id.lumut.democleanarch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import id.lumut.democleanarch.data.repository.AuthRepositoryImpl
import id.lumut.democleanarch.domain.usecase.LoginUseCase
import id.lumut.democleanarch.presentation.LoginScreen
import id.lumut.democleanarch.presentation.LoginViewModel
import id.lumut.democleanarch.presentation.theme.DemoCleanArchTheme

/**
 * MainActivity — ENTRY POINT saja.
 *
 * :app module HANYA berisi:
 * - MainActivity (DI wiring)
 * - AndroidManifest.xml
 * - Resources (icon, theme, strings)
 *
 * TIDAK ADA business logic atau UI di sini.
 *
 * Dependency:
 *   AuthRepositoryImpl → dari :data
 *   LoginUseCase       → dari :domain
 *   LoginViewModel     → dari :presentation
 *   LoginScreen        → dari :presentation
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Manual DI — wiring 3 layer module
        val authRepository = AuthRepositoryImpl()       // :data
        val loginUseCase = LoginUseCase(authRepository)  // :domain
        val loginViewModel = LoginViewModel(loginUseCase) // :presentation

        setContent {
            DemoCleanArchTheme {
                LoginScreen(viewModel = loginViewModel)  // :presentation
            }
        }
    }
}
