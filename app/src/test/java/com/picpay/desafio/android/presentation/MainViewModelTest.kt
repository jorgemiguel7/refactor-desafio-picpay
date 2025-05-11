package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.core.error.toNetworkException
import com.picpay.desafio.android.core.result.Result
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: MainViewModel

    private val usersObserver = mockk<Observer<List<User>>>(relaxed = true)
    private val isLoadingObserver = mockk<Observer<Boolean>>(relaxed = true)
    private val errorObserver = mockk<Observer<Boolean>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel = MainViewModel(userRepository)

        viewModel.users.observeForever(usersObserver)
        viewModel.isLoading.observeForever(isLoadingObserver)
        viewModel.error.observeForever(errorObserver)
    }

    @Test
    fun `Given successful fetch, When fetchUsers is called, Then update LiveData with users`() =
        runTest {
            val userList = listOf(
                User(id = 1, name = "John Doe", username = "johndoe", image = "avatar_url"),
                User(id = 2, name = "Jane Doe", username = "janedoe", image = "avatar_url")
            )
            coEvery { userRepository.getUsers() } returns flowOf(Result.Success(userList))

            viewModel.fetchUsers()

            coVerify { usersObserver.onChanged(userList) }
            coVerify { isLoadingObserver.onChanged(false) }
            coVerify { errorObserver.onChanged(false) }
        }

    @Test
    fun `Given failed fetch, When fetchUsers is called, Then update LiveData with error state`() =
        runTest {
            coEvery { userRepository.getUsers() } returns flowOf(Result.Error(Exception().toNetworkException()))

            viewModel.fetchUsers()

            coVerify { usersObserver wasNot Called }
            coVerify { isLoadingObserver.onChanged(false) }
            coVerify { errorObserver.onChanged(true) }
        }
}