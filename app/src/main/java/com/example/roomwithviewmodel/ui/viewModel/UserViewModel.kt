package com.example.roomwithviewmodel.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomwithviewmodel.data.SortType
import com.example.roomwithviewmodel.data.UserEvent
import com.example.roomwithviewmodel.data.UserState
import com.example.roomwithviewmodel.room.dao.UserDao
import com.example.roomwithviewmodel.room.entity.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModel(
    private val dao: UserDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)

    private val _users = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.FIRST_NAME -> dao.getUsersOrderedByFirstName()
                SortType.LAST_NAME -> dao.getUsersOrderedByLastName()
                SortType.PHONE_NUMBER -> dao.getUsersOrderedByPhoneNumber()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(UserState())
    val state = combine(_state, _sortType, _users) { state, sortType, users ->
        state.copy(
            users = users,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserState())

    fun onEvent(event: UserEvent) {
        when(event) {
            is UserEvent.DeleteUser -> {
                viewModelScope.launch {
                    dao.deleteUser(event.user)
                }
            }
            UserEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingUser = false
                ) }
            }
            UserEvent.SaveUser -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber

                if(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                    return
                }

                val user = UserEntity(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )
                viewModelScope.launch {
                    dao.upsertUser(user)
                }
                _state.update { it.copy(
                    isAddingUser = false,
                    firstName = "",
                    lastName = "",
                    phoneNumber = ""
                ) }
            }
            is UserEvent.SetFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                ) }
            }
            is UserEvent.SetLastName -> {
                _state.update { it.copy(
                    lastName = event.lastName
                ) }
            }
            is UserEvent.SetPhoneNumber -> {
                _state.update { it.copy(
                    phoneNumber = event.phoneNumber
                ) }
            }
            UserEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingUser = true
                ) }
            }
            is UserEvent.SortUsers -> {
                _sortType.value = event.sortType
            }
        }
    }
}