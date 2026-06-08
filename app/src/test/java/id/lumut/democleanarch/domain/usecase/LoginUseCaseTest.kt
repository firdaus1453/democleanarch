package id.lumut.democleanarch.domain.usecase

import id.lumut.democleanarch.domain.model.User
import id.lumut.democleanarch.domain.repository.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit test untuk LoginUseCase.
 *
 * Karena UseCase hanya bergantung ke INTERFACE (AuthRepository),
 * kita bisa dengan mudah membuat FAKE implementation untuk testing.
 *
 * Ini adalah salah satu benefit utama Separation of Concern:
 * → TESTABLE — mudah di-mock karena layer tidak dependent ke layer lain
 */
class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var fakeRepository: FakeAuthRepository

    @Before
    fun setup() {
        fakeRepository = FakeAuthRepository()
        loginUseCase = LoginUseCase(fakeRepository)
    }

    // ─── Validasi Input Tests ────────────────────────────────

    @Test
    fun `login with empty email returns failure`() = runTest {
        val result = loginUseCase("", "password123")

        assertTrue(result.isFailure)
        assertEquals("Email tidak boleh kosong", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with empty password returns failure`() = runTest {
        val result = loginUseCase("admin@test.com", "")

        assertTrue(result.isFailure)
        assertEquals("Password tidak boleh kosong", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with invalid email format returns failure`() = runTest {
        val result = loginUseCase("invalid-email", "password123")

        assertTrue(result.isFailure)
        assertEquals("Format email tidak valid", result.exceptionOrNull()?.message)
    }

    // ─── Success Tests ──────────────────────────────────────

    @Test
    fun `login with valid credentials returns success`() = runTest {
        fakeRepository.shouldSucceed = true

        val result = loginUseCase("admin@test.com", "password123")

        assertTrue(result.isSuccess)
        assertEquals("Admin User", result.getOrNull()?.name)
    }

    // ─── Repository Error Tests ─────────────────────────────

    @Test
    fun `login with wrong credentials returns failure from repository`() = runTest {
        fakeRepository.shouldSucceed = false

        val result = loginUseCase("admin@test.com", "wrongpassword")

        assertTrue(result.isFailure)
        assertEquals("Email atau password salah", result.exceptionOrNull()?.message)
    }

    // ─── Fake Repository ────────────────────────────────────

    /**
     * Fake implementation untuk testing.
     * Tidak perlu library mocking — cukup implement interface!
     * Ini menunjukkan kekuatan Dependency Inversion.
     */
    private class FakeAuthRepository : AuthRepository {
        var shouldSucceed = true

        override suspend fun login(email: String, password: String): Result<User> {
            return if (shouldSucceed) {
                Result.success(User(id = "1", email = email, name = "Admin User"))
            } else {
                Result.failure(Exception("Email atau password salah"))
            }
        }
    }
}
