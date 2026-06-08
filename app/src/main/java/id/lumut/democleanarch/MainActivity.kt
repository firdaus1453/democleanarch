package id.lumut.democleanarch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import id.lumut.democleanarch.auth.data.repository.AuthRepositoryImpl
import id.lumut.democleanarch.auth.domain.usecase.LoginUseCase
import id.lumut.democleanarch.auth.presentation.LoginScreen
import id.lumut.democleanarch.auth.presentation.LoginViewModel
import id.lumut.democleanarch.home.data.repository.UserRepositoryImpl
import id.lumut.democleanarch.home.domain.usecase.GetCurrentUserUseCase
import id.lumut.democleanarch.home.domain.usecase.LogoutUseCase
import id.lumut.democleanarch.home.presentation.HomeScreen
import id.lumut.democleanarch.home.presentation.HomeViewModel
import id.lumut.democleanarch.ui.theme.DemoCleanArchTheme

/**
 * MainActivity — ORCHESTRATOR (Philipp Lackner style).
 *
 * :app = satu-satunya module yang tahu SEMUA module.
 * DI wiring & navigasi dilakukan di sini.
 *
 * Import dari 2 feature module:
 *   Auth: AuthRepositoryImpl, LoginUseCase, LoginViewModel, LoginScreen
 *   Home: UserRepositoryImpl, GetCurrentUserUseCase, LogoutUseCase, HomeViewModel, HomeScreen
 *
 * ❌ :auth TIDAK tahu tentang :home
 * ❌ :home TIDAK tahu tentang :auth
 * ✅ Kedua feature pakai User dari :core:domain
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // === DI wiring: Auth feature ===
        val authRepository = AuthRepositoryImpl()
        val loginUseCase = LoginUseCase(authRepository)
        val loginViewModel = LoginViewModel(loginUseCase)

        // === DI wiring: Home feature ===
        val userRepository = UserRepositoryImpl()
        val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository)
        val logoutUseCase = LogoutUseCase(userRepository)
        val homeViewModel = HomeViewModel(getCurrentUserUseCase, logoutUseCase)

        setContent {
            DemoCleanArchTheme {
                // Simple screen navigation state
                var currentScreen by remember { mutableStateOf("login") }

                when (currentScreen) {
                    "login" -> LoginScreen(
                        viewModel = loginViewModel,
                        onLoginSuccess = { currentScreen = "home" }
                    )
                    "home" -> HomeScreen(
                        viewModel = homeViewModel,
                        onLoggedOut = { currentScreen = "login" }
                    )
                }
            }
        }
    }
}
