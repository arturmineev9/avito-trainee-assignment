package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEvent
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthState
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.R

@Composable
fun AuthModeToggleButton(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit
) {
    TextButton(
        onClick = { onEvent(AuthEvent.ToggleAuthModeClicked) },
        enabled = !state.isLoading
    ) {
        Text(
            text = if (state.isLoginMode)
                stringResource(R.string.auth_no_account_prompt)
            else
                stringResource(R.string.auth_already_have_account_prompt)
        )
    }
}
