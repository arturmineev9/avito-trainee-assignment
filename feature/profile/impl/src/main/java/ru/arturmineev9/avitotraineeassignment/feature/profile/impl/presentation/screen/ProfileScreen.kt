package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileEvent
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileState
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.R
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.AvatarSection
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.FullScreenLoader
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.LogoutButton
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.ThemeSettingsSection
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.UserInfoSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    snackBarHostState: SnackbarHostState,
    onEvent: (ProfileEvent) -> Unit,
    onPickPhoto: () -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { TopAppBar(title = { Text(stringResource(R.string.profile_title), fontWeight = FontWeight.Bold) }) }
    ) { padding ->
        if (state.isLoading) {
            FullScreenLoader()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AvatarSection(
                    photoUrl = state.profile?.photoUrl,
                    isUploading = state.isUploadingImage,
                    onPickPhoto = onPickPhoto
                )

                Spacer(Modifier.height(32.dp))

                UserInfoSection(
                    state = state,
                    onEvent = onEvent
                )

                Spacer(Modifier.height(16.dp))

                ThemeSettingsSection(
                    isDarkTheme = state.isDarkTheme,
                    onThemeToggle = { onEvent(ProfileEvent.ThemeToggled(it)) }
                )

                Spacer(Modifier.weight(1f))
                Spacer(Modifier.height(32.dp))

                LogoutButton(onLogout = { onEvent(ProfileEvent.LogoutClicked) })
            }
        }
    }
}
