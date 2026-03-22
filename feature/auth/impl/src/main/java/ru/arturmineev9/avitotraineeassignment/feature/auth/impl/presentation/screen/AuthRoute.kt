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
import kotlinx.coroutines.flow.collectLatest
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEffect
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.mapper.toUiText
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.viewmodel.AuthViewModel

@Composable
fun AuthRoute(
    navigateToChats: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffect.NavigateToChats -> navigateToChats()
                is AuthEffect.ShowError -> {
                    snackBarHostState.showSnackbar(
                        message = effect.error.toUiText(context),
                        duration = SnackbarDuration.Short
                    )
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
