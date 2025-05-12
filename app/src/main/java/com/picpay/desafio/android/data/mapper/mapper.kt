package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.local.entity.UserEntity
import com.picpay.desafio.android.data.remote.model.UserDto
import com.picpay.desafio.android.domain.model.User

fun UserDto.toDomain(): User {
    return User(id = id, name = name, username = username, image = image)
}

fun UserEntity.toDomain(): User {
    return User(id = id, name = name, username = username, image = image)
}

fun UserDto.toEntity(): UserEntity {
    return UserEntity(id = id, name = name, username = username, image = image)
}