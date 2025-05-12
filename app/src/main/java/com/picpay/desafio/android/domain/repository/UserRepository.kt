package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.core.error.DataError
import com.picpay.desafio.android.core.result.Result
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Result<List<User>, DataError.Remote>>
}