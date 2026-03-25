package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEvent
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthState
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.R

@Composable
fun PrimaryAuthButton(
    state: AuthState,
    focusManager: FocusManager,
    onEvent: (AuthEvent) -> Unit
) {
    Button(
        onClick = {
            focusManager.clearFocus()
            onEvent(AuthEvent.SubmitClicked)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = !state.isLoading
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = if (state.isLoginMode)
                    stringResource(R.string.auth_login_button)
                else
                    stringResource(R.string.auth_register_button)
            )
        }
    }
}
