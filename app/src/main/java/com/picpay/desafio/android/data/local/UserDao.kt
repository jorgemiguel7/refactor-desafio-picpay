package com.picpay.desafio.android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.picpay.desafio.android.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    @Transaction
    suspend fun updateUsers(users: List<UserEntity>) {
        clearUsers()
        insertUsers(users)
    }
}