package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.mapper.toDomain
import com.picpay.desafio.android.data.remote.api.PicPayService
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val picPayService: PicPayService) : UserRepository {

    // TODO: Adicionar logica de cache
    // TODO: Fazer o tratamento de exception
    override suspend fun getUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                picPayService.getUsers().map { it.toDomain() }
            } catch (exception: Exception) {
                emptyList()
            }
        }
    }
}