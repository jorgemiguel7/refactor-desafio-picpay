package com.picpay.desafio.android.data

import com.picpay.desafio.android.core.error.DataError
import com.picpay.desafio.android.core.error.toNetworkException
import com.picpay.desafio.android.core.result.Result
import com.picpay.desafio.android.data.mapper.toDomain
import com.picpay.desafio.android.data.remote.api.PicPayService
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val picPayService: PicPayService) : UserRepository {

    override suspend fun getUsers(): Result<List<User>, DataError> {
        return withContext(Dispatchers.IO) {
            try {
                val result = picPayService.getUsers().map { it.toDomain() }
                Result.Success(result)
            } catch (exception: Exception) {
                Result.Error(exception.toNetworkException())
            }
        }
    }
}