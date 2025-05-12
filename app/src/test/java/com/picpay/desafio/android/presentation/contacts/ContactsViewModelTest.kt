package com.picpay.desafio.android.presentation.contacts

import com.picpay.desafio.android.core.result.Result
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository = mockk<UserRepository>(relaxed = true)
    private lateinit var viewModel: ContactsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given users are successfully fetched, When fetchUsers is called, Then uiState should be updated with users and isLoading should be false`() =
        runTest {
            val users = listOf(
                User(id = 1, name = "User 1", username = "user1", image = "url1"),
                User(id = 2, name = "User 2", username = "user2", image = "url2")
            )

            coEvery { repository.getUsers() } returns flowOf(Result.Success(users))

            viewModel = ContactsViewModel(repository)

            with(viewModel.uiState.value) {
                assertEquals(false, isLoading)
                assertEquals(users, users)
            }
        }
}