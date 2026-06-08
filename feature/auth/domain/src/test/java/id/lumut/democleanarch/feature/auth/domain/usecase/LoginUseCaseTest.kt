package id.lumut.democleanarch.feature.auth.domain.usecase

import id.lumut.democleanarch.core.common.AppResult
import id.lumut.democleanarch.core.common.DataError
import id.lumut.democleanarch.feature.auth.domain.model.User
import id.lumut.democleanarch.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Test untuk LoginUseCase di :feature:auth:domain module.
 *
 * Test ini dijalankan HANYA dalam scope :feature:auth:domain.
 * Perubahan di :feature:home tidak akan mempengaruhi test ini.
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
    fun `login with empty email returns Authentication error`() = runTest {
        val result = loginUseCase("", "password123")
        assertTrue(result is AppResult.Error)
        val error = (result as AppResult.Error).error
        assertTrue(error is DataError.Authentication)
        assertEquals("Email tidak boleh kosong", (error as DataError.Authentication).message)
    }

    @Test
    fun `login with empty password returns Authentication error`() = runTest {
        val result = loginUseCase("admin@test.com", "")
        assertTrue(result is AppResult.Error)
    }

    @Test
    fun `login with invalid email returns Authentication error`() = runTest {
        val result = loginUseCase("invalid-email", "password123")
        assertTrue(result is AppResult.Error)
    }

    @Test
    fun `login with valid credentials returns Success`() = runTest {
        fakeRepository.resultToReturn = AppResult.Success(
            User(id = "1", email = "admin@test.com", name = "Admin User")
        )
        val result = loginUseCase("admin@test.com", "password123")
        assertTrue(result is AppResult.Success)
        assertEquals("Admin User", (result as AppResult.Success).data.name)
    }

    @Test
    fun `login with wrong credentials returns Error from repository`() = runTest {
        fakeRepository.resultToReturn = AppResult.Error(
            DataError.Authentication("Email atau password salah")
        )
        val result = loginUseCase("admin@test.com", "wrong")
        assertTrue(result is AppResult.Error)
    }

    private class FakeAuthRepository : AuthRepository {
        var resultToReturn: AppResult<User> = AppResult.Success(
            User(id = "1", email = "test@test.com", name = "Test User")
        )

        override suspend fun login(email: String, password: String): AppResult<User> {
            return resultToReturn
        }
    }
}
