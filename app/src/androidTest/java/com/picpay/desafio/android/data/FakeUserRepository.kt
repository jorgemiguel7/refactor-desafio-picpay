package com.picpay.desafio.android.data

import com.picpay.desafio.android.core.error.DataError
import com.picpay.desafio.android.core.error.toNetworkException
import com.picpay.desafio.android.core.result.Result
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeUserRepository : UserRepository {
    private val _usersFlow =
        MutableStateFlow<Result<List<User>, DataError.Remote>>(Result.Success(emptyList()))

    fun emitUsers(users: List<User>) {
        _usersFlow.value = Result.Success(users)
    }

    fun emitError(exception: Exception) {
        _usersFlow.value = Result.Error(exception.toNetworkException())
    }

    override fun getUsers(): Flow<Result<List<User>, DataError.Remote>> = _usersFlow.asStateFlow()
}