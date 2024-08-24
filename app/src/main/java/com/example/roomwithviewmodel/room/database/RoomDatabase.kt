package com.example.roomwithviewmodel.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomwithviewmodel.room.dao.UserDao
import com.example.roomwithviewmodel.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class RoomDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}