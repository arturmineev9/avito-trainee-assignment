package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEvent
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthState
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.components.AuthInputFields
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.components.AuthModeToggleButton
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.components.GoogleSignInButton
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.components.PrimaryAuthButton
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    state: AuthState,
    snackBarHostState: SnackbarHostState,
    onEvent: (AuthEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.auth_screen_title)) }) },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 50 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AuthInputFields(state = state, focusManager = focusManager, onEvent = onEvent)

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryAuthButton(state = state, focusManager = focusManager, onEvent = onEvent)

                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(R.string.auth_or_divider), color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(16.dp))

                GoogleSignInButton(state = state, onEvent = onEvent)

                Spacer(modifier = Modifier.height(16.dp))

                AuthModeToggleButton(state = state, onEvent = onEvent)
            }
        }
    }
}
