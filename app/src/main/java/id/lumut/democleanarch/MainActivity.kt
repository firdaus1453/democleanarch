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
 * MainActivity — titik masuk aplikasi (Multi Module version).
 *
 * Di sini kita melakukan wiring/DI:
 * - AuthRepositoryImpl dari :data module
 * - LoginUseCase dari :domain module
 * - LoginViewModel dari :app module (presentation)
 *
 * HANYA di sini (:app) kita boleh tahu tentang :data module.
 * ViewModel dan UseCase TIDAK tahu tentang :data.
 *
 * Dependency graph:
 *   :app → :domain (:app import UseCase, Model)
 *   :app → :data   (:app import RepositoryImpl untuk DI)
 *   :data → :domain (:data implement interface domain)
 *   :domain → (nothing) — domain independent!
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Manual DI — wiring antar module
        val authRepository = AuthRepositoryImpl()       // dari :data
        val loginUseCase = LoginUseCase(authRepository)  // dari :domain
        val loginViewModel = LoginViewModel(loginUseCase) // dari :app

        setContent {
            DemoCleanArchTheme {
                LoginScreen(viewModel = loginViewModel)
            }
        }
    }
}
