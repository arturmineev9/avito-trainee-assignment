package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEvent
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthState
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.R
@Composable
fun AuthInputFields(
    state: AuthState,
    focusManager: FocusManager,
    onEvent: (AuthEvent) -> Unit
) {
    OutlinedTextField(
        value = state.emailInput,
        onValueChange = { onEvent(AuthEvent.EmailChanged(it)) },
        label = { Text(stringResource(R.string.auth_email_label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        enabled = !state.isLoading
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = state.passwordInput,
        onValueChange = { onEvent(AuthEvent.PasswordChanged(it)) },
        label = { Text(stringResource(R.string.auth_password_label)) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onEvent(AuthEvent.SubmitClicked)
            }
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        enabled = !state.isLoading
    )
}
