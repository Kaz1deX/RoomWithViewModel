package com.example.roomwithviewmodel.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.roomwithviewmodel.R
import com.example.roomwithviewmodel.data.UserEvent
import com.example.roomwithviewmodel.data.UserState

@Composable
fun UserDialog(
    state: UserState,
    onEvent: (UserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(UserEvent.HideDialog)
        },
        confirmButton = {

        },
        title = {
            Text(text = stringResource(R.string.add_user))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.firstName,
                    onValueChange = { firstName ->
                        onEvent(UserEvent.SetFirstName(firstName))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.first_name))
                    }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = { lastName ->
                        onEvent(UserEvent.SetLastName(lastName))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.last_name))
                    }
                )
                TextField(
                    value = state.phoneNumber,
                    onValueChange = { phoneNumber ->
                        onEvent(UserEvent.SetPhoneNumber(phoneNumber))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.phone_number))
                    }
                )
            }
        },
        dismissButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = {
                        onEvent(UserEvent.SaveUser)
                    }
                ) {
                    Text(text = stringResource(R.string.save_user))
                }
            }
        }
    )
}