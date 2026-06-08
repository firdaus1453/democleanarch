package id.lumut.democleanarch.feature.auth.presentation

import id.lumut.democleanarch.core.common.AppResult
import id.lumut.democleanarch.core.common.DataError
import id.lumut.democleanarch.feature.auth.domain.model.User
import id.lumut.democleanarch.feature.auth.domain.repository.AuthRepository
import id.lumut.democleanarch.feature.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Test untuk LoginViewModel di :feature:auth:presentation.
 * Menggunakan fake dari :feature:auth:domain interface.
 * TIDAK perlu :feature:auth:data untuk testing.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel
    private lateinit var fakeRepository: FakeAuthRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeAuthRepository()
        val loginUseCase = LoginUseCase(fakeRepository)
        viewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Idle`() {
        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `login with valid credentials updates state to Success`() = runTest {
        fakeRepository.resultToReturn = AppResult.Success(
            User(id = "1", email = "admin@test.com", name = "Admin User")
        )
        viewModel.onEmailChange("admin@test.com")
        viewModel.onPasswordChange("password123")

        viewModel.login()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue("Expected Success but got $state", state is LoginUiState.Success)
        assertEquals("Admin User", (state as LoginUiState.Success).user.name)
    }

    @Test
    fun `login with empty email updates state to Error`() = runTest {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("password123")

        viewModel.login()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue("Expected Error but got $state", state is LoginUiState.Error)
    }

    @Test
    fun `login failure updates state to Error with message`() = runTest {
        fakeRepository.resultToReturn = AppResult.Error(
            DataError.Authentication("Email atau password salah")
        )
        viewModel.onEmailChange("admin@test.com")
        viewModel.onPasswordChange("wrong")

        viewModel.login()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Error)
        assertEquals("Email atau password salah", (state as LoginUiState.Error).message)
    }

    @Test
    fun `typing after error resets state to Idle`() = runTest {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("password123")
        viewModel.login()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LoginUiState.Error)
        viewModel.onEmailChange("a")
        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
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
