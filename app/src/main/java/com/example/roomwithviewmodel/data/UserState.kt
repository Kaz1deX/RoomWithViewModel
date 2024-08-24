package com.example.roomwithviewmodel.data

import com.example.roomwithviewmodel.room.entity.UserEntity

data class UserState(
    val users: List<UserEntity> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingUser: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)