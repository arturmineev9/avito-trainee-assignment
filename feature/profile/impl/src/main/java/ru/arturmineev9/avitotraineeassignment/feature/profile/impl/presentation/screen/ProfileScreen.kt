package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileEvent
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileState
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.AvatarSection
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.FullScreenLoader
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.LogoutButton
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.ThemeSettingsSection
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components.UserInfoSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    state: ProfileState,
    snackBarHostState: SnackbarHostState,
    onEvent: (ProfileEvent) -> Unit,
    onPickPhoto: () -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { TopAppBar(title = { Text("Профиль", fontWeight = FontWeight.Bold) }) }
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
