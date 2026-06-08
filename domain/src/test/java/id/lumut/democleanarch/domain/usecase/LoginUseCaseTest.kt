package id.lumut.democleanarch.domain.usecase

import id.lumut.democleanarch.domain.model.User
import id.lumut.democleanarch.domain.repository.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit test untuk LoginUseCase di module :domain.
 *
 * Test ini bisa dijalankan SECARA INDEPENDENT dari :app dan :data.
 * Gradle hanya perlu compile :domain module saja.
 */
class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var fakeRepository: FakeAuthRepository

    @Before
    fun setup() {
        fakeRepository = FakeAuthRepository()
        loginUseCase = LoginUseCase(fakeRepository)
    }

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

    @Test
    fun `login with valid credentials returns success`() = runTest {
        fakeRepository.shouldSucceed = true
        val result = loginUseCase("admin@test.com", "password123")
        assertTrue(result.isSuccess)
        assertEquals("Admin User", result.getOrNull()?.name)
    }

    @Test
    fun `login with wrong credentials returns failure from repository`() = runTest {
        fakeRepository.shouldSucceed = false
        val result = loginUseCase("admin@test.com", "wrongpassword")
        assertTrue(result.isFailure)
    }

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
