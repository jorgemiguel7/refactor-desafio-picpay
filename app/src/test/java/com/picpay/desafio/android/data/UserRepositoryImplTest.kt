package com.picpay.desafio.android.data

import com.picpay.desafio.android.core.error.DataError
import com.picpay.desafio.android.core.result.Result
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.mapper.toEntity
import com.picpay.desafio.android.data.remote.api.PicPayService
import com.picpay.desafio.android.data.remote.model.UserDto
import com.picpay.desafio.android.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class UserRepositoryImplTest {
    private val picPayService = mockk<PicPayService>()
    private val userDao = mockk<UserDao>()
    private val userRepository = UserRepositoryImpl(picPayService, userDao)

    @Test
    fun `Given remote data, When getUsers is called, Then return success with remote data`() =
        runTest {
            val expected = getUsers()

            coEvery { picPayService.getUsers() } returns getUsersDto()
            coEvery { userDao.updateUsers(any()) } returns Unit

            val result = userRepository.getUsers().first()

            assertIs<Result.Success<List<User>>>(result)
            assertEquals(expected, result.data)

            coVerify { userDao.updateUsers(any()) }
        }

    @Test
    fun `Given remote error, When getUsers is called, Then return success with cached data`() =
        runTest {
            val expected = getUsers()
            val usersEntity = getUsersDto().map { it.toEntity() }

            coEvery { picPayService.getUsers() } throws Exception()
            coEvery { userDao.getUsers() } returns usersEntity
            coEvery { userDao.updateUsers(any()) } returns Unit

            val result = userRepository.getUsers().first()

            assertIs<Result.Success<List<User>>>(result)
            assertEquals(expected, result.data)

            coVerify { userDao.getUsers() }
        }

    @Test
    fun `Given remote error and no cached data, When getUsers is called, Then return error`() =
        runTest {
            coEvery { picPayService.getUsers() } throws Exception()
            coEvery { userDao.getUsers() } throws Exception()

            val result = userRepository.getUsers().first()

            assertIs<Result.Error<DataError.Remote>>(result)

            coVerify { userDao.getUsers() }
        }

    private fun getUsers(): List<User> = listOf(
        User(id = 1, name = "John Doe", image = "avatar_url", username = "John"),
        User(id = 2, name = "Cena", image = "avatar_url", username = "Cena")
    )

    private fun getUsersDto(): List<UserDto> = listOf(
        UserDto(id = 1, name = "John Doe", image = "avatar_url", username = "John"),
        UserDto(id = 2, name = "Cena", image = "avatar_url", username = "Cena")
    )
}