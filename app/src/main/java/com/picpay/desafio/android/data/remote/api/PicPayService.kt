package com.picpay.desafio.android.data.remote.api

import com.picpay.desafio.android.data.remote.model.UserDto
import retrofit2.http.GET

interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}