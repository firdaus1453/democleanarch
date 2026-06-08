package id.lumut.democleanarch.presentation

import id.lumut.democleanarch.domain.model.User
import id.lumut.democleanarch.domain.repository.AuthRepository
import id.lumut.democleanarch.domain.usecase.LoginUseCase
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
 * Unit test untuk LoginViewModel.
 *
 * Kita test bahwa ViewModel:
 * - Mengubah state dengan benar berdasarkan hasil UseCase
 * - Mengelola input (email, password) dengan benar
 *
 * Karena ViewModel hanya bergantung ke UseCase (domain),
 * dan UseCase bergantung ke interface — kita bisa test secara ISOLATED.
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
    fun `email and password update correctly`() {
        viewModel.onEmailChange("test@email.com")
        viewModel.onPasswordChange("secret")

        assertEquals("test@email.com", viewModel.email.value)
        assertEquals("secret", viewModel.password.value)
    }

    @Test
    fun `login with valid credentials updates state to Success`() = runTest {
        fakeRepository.shouldSucceed = true
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
        assertEquals("Email tidak boleh kosong", (state as LoginUiState.Error).message)
    }

    @Test
    fun `login with wrong credentials updates state to Error`() = runTest {
        fakeRepository.shouldSucceed = false
        viewModel.onEmailChange("admin@test.com")
        viewModel.onPasswordChange("wrong")

        viewModel.login()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue("Expected Error but got $state", state is LoginUiState.Error)
    }

    @Test
    fun `typing after error resets state to Idle`() = runTest {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("password123")
        viewModel.login()
        advanceUntilIdle()

        // State should be Error
        assertTrue(viewModel.uiState.value is LoginUiState.Error)

        // Typing should reset to Idle
        viewModel.onEmailChange("a")
        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
    }

    // ─── Fake Repository ────────────────────────────────────

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
