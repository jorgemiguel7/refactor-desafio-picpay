package com.picpay.desafio.android.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("img") val image: String,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String
)