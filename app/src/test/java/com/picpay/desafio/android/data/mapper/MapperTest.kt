package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.local.entity.UserEntity
import com.picpay.desafio.android.data.remote.model.UserDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `Given UserDto, When converted to Domain, Then return User`() {
        val userDto = UserDto(id = 1, name = "John Doe", username = "johndoe", image = "avatar_url")

        val user = userDto.toDomain()

        assertEquals(user.id, userDto.id)
        assertEquals(user.name, userDto.name)
        assertEquals(user.username, userDto.username)
        assertEquals(user.image, userDto.image)
    }

    @Test
    fun `Given UserEntity, When converted to Domain, Then return User`() {
        val userEntity =
            UserEntity(id = 1, name = "John Doe", username = "johndoe", image = "avatar_url")

        val user = userEntity.toDomain()

        assertEquals(user.id, userEntity.id)
        assertEquals(user.name, userEntity.name)
        assertEquals(user.username, userEntity.username)
        assertEquals(user.image, userEntity.image)
    }

    @Test
    fun `Given UserDto, When converted to Entity, Then return UserEntity`() {
        val userDto = UserDto(id = 1, name = "John Doe", username = "johndoe", image = "avatar_url")

        val userEntity = userDto.toEntity()

        assertEquals(userEntity.id, userDto.id)
        assertEquals(userEntity.name, userDto.name)
        assertEquals(userEntity.username, userDto.username)
        assertEquals(userEntity.image, userDto.image)
    }
}