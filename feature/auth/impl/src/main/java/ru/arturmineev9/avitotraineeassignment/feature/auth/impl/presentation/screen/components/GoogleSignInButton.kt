package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEvent
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthState
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.R

@Composable
fun GoogleSignInButton(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit
) {
    OutlinedButton(
        onClick = { onEvent(AuthEvent.GoogleSignInClicked) },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = !state.isLoading
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(stringResource(R.string.auth_google_sign_in))
        }
    }
}
