package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.core.error.DataError
import com.picpay.desafio.android.core.result.Result
import com.picpay.desafio.android.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>, DataError>
}