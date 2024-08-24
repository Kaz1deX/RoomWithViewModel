package com.example.roomwithviewmodel.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.roomwithviewmodel.room.database.RoomDatabase
import com.example.roomwithviewmodel.ui.screen.UserScreen
import com.example.roomwithviewmodel.ui.theme.RoomWithViewModelTheme
import com.example.roomwithviewmodel.ui.viewModel.UserViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java,
            "room_db"
        ).build()
    }

    private val viewModel by viewModels<UserViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(db.userDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomWithViewModelTheme {
                val state by viewModel.state.collectAsState()
                UserScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}