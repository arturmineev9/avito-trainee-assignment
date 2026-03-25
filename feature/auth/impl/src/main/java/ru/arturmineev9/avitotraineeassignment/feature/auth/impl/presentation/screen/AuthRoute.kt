package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.arturmineev9.avitotraineeassignment.core.navigation.navigator.AuthNavigator
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEffect
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEvent
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.mapper.toUiText
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.google.launchGoogleSignIn
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.viewmodel.AuthViewModel

@Composable
fun AuthRoute(
    navigator: AuthNavigator,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is AuthEffect.NavigateToChats -> navigator.navigateToChats()
                    is AuthEffect.ShowError -> {
                        snackBarHostState.showSnackbar(
                            message = effect.error.toUiText(context),
                            duration = SnackbarDuration.Short
                        )
                    }

                    is AuthEffect.LaunchGoogleSignIn -> {
                        val result = launchGoogleSignIn(context)

                        result.onSuccess { idToken ->
                            viewModel.onEvent(AuthEvent.GoogleTokenReceived(idToken))
                        }.onFailure { error ->
                            viewModel.onEvent(
                                AuthEvent.GoogleSignInFailed(
                                    error.message ?: "Ошибка"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    AuthScreen(
        state = state,
        snackBarHostState = snackBarHostState,
        onEvent = viewModel::onEvent
    )
}
