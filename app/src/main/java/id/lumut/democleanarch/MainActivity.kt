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
 * MainActivity — titik masuk aplikasi.
 *
 * Di sini kita melakukan MANUAL Dependency Injection:
 * 1. Buat instance AuthRepositoryImpl (data layer)
 * 2. Buat instance LoginUseCase dengan inject repository (domain layer)
 * 3. Buat instance LoginViewModel dengan inject use case (presentation layer)
 *
 * Flow dependency:
 * MainActivity → ViewModel → UseCase → Repository(interface)
 *                                          ↑
 *                              RepositoryImpl (data layer)
 *
 * Dalam production app, DI biasanya menggunakan Hilt/Koin.
 * Di demo ini kita pakai manual DI agar fokus ke arsitektur.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Manual Dependency Injection
        val authRepository = AuthRepositoryImpl()
        val loginUseCase = LoginUseCase(authRepository)
        val loginViewModel = LoginViewModel(loginUseCase)

        setContent {
            DemoCleanArchTheme {
                LoginScreen(viewModel = loginViewModel)
            }
        }
    }
}
