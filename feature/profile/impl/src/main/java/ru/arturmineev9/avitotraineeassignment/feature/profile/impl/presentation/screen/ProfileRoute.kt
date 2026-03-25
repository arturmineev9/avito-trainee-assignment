package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileEffect
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileEvent
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.viewmodel.ProfileViewModel
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.mapper.toUiText

@Composable
fun ProfileRoute(
    onLogoutSuccess: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.onEvent(ProfileEvent.AvatarSelected(it)) }
    }

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is ProfileEffect.NavigateToAuth -> onLogoutSuccess()

                    is ProfileEffect.ShowError -> {
                        val message = effect.error.toUiText(context)
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short
                        )
                    }

                    is ProfileEffect.ShowMessage -> {
                        snackBarHostState.showSnackbar(
                            message = effect.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    ProfileScreen(
        state = state,
        snackBarHostState = snackBarHostState,
        onEvent = viewModel::onEvent,
        onPickPhoto = {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    )
}
