package com.example.roomwithviewmodel.data

import com.example.roomwithviewmodel.room.entity.UserEntity

sealed interface UserEvent {
    data object SaveUser: UserEvent
    data class SetFirstName(val firstName: String): UserEvent
    data class SetLastName(val lastName: String): UserEvent
    data class SetPhoneNumber(val phoneNumber: String): UserEvent
    data object ShowDialog: UserEvent
    data object HideDialog: UserEvent
    data class SortUsers(val sortType: SortType): UserEvent
    data class DeleteUser(val user: UserEntity): UserEvent
}