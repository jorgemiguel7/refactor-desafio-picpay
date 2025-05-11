package com.picpay.desafio.android.data

import com.picpay.desafio.android.core.error.DataError
import com.picpay.desafio.android.core.error.toNetworkException
import com.picpay.desafio.android.core.result.Result
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.mapper.toDomain
import com.picpay.desafio.android.data.mapper.toEntity
import com.picpay.desafio.android.data.remote.api.PicPayService
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val picPayService: PicPayService,
    private val userDao: UserDao
) : UserRepository {

    override fun getUsers(): Flow<Result<List<User>, DataError.Remote>> = flow {
        try {
            val remote = picPayService.getUsers()
            emit(Result.Success(remote.map { it.toDomain() }))

            runCatching {
                userDao.updateUsers(remote.map { it.toEntity() })
            }
        } catch (e: Exception) {
            runCatching {
                val cached = userDao.getUsers().map { it.toDomain() }
                emit(Result.Success(cached))
            }.onFailure {
                emit(Result.Error(e.toNetworkException()))
            }
        }
    }.flowOn(Dispatchers.IO)
}