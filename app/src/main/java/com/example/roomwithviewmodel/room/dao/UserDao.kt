package com.example.roomwithviewmodel.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.roomwithviewmodel.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user_table ORDER BY firstName ASC")
    fun getUsersOrderedByFirstName(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table ORDER BY lastName ASC")
    fun getUsersOrderedByLastName(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table ORDER BY phoneNumber ASC")
    fun getUsersOrderedByPhoneNumber(): Flow<List<UserEntity>>
}