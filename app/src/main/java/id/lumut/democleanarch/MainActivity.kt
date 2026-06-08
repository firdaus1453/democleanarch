package id.lumut.democleanarch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import id.lumut.democleanarch.feature.auth.data.repository.AuthRepositoryImpl
import id.lumut.democleanarch.feature.auth.domain.usecase.LoginUseCase
import id.lumut.democleanarch.feature.auth.presentation.LoginScreen
import id.lumut.democleanarch.feature.auth.presentation.LoginViewModel
import id.lumut.democleanarch.ui.theme.DemoCleanArchTheme

/**
 * MainActivity — ORCHESTRATOR dari Feature Layered Multi Module.
 *
 * :app module adalah satu-satunya yang tahu SEMUA module.
 * Di sinilah DI wiring dilakukan:
 *
 * Import dari berbagai feature module:
 * - AuthRepositoryImpl  → dari :feature:auth:data
 * - LoginUseCase        → dari :feature:auth:domain
 * - LoginViewModel      → dari :feature:auth:presentation
 * - LoginScreen         → dari :feature:auth:presentation
 *
 * Jika pakai Hilt/Koin, DI wiring ini akan di-handle oleh DI framework.
 *
 * ═══════════════════════════════════════════════════════════
 * Perbandingan build performance vs 3 Layer:
 * ═══════════════════════════════════════════════════════════
 *
 * 3 Layer (ada 15 feature di module :domain):
 *   Ubah 1 feature di :domain → rebuild SEMUA 15 feature ❌
 *
 * Feature Layered (ada 15 feature terpisah):
 *   Ubah :feature:auth:domain → rebuild HANYA auth module ✅
 *   Feature lain (home, profile, dll) TIDAK ter-rebuild
 * ═══════════════════════════════════════════════════════════
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Manual DI — wiring feature modules
        val authRepository = AuthRepositoryImpl()          // :feature:auth:data
        val loginUseCase = LoginUseCase(authRepository)     // :feature:auth:domain
        val loginViewModel = LoginViewModel(loginUseCase)   // :feature:auth:presentation

        setContent {
            DemoCleanArchTheme {
                LoginScreen(viewModel = loginViewModel)
            }
        }
    }
}
