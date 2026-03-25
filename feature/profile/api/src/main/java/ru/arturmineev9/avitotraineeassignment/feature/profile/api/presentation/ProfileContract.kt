package ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation

import android.net.Uri
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiEffect
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiEvent
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiState
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.model.UserProfile

data class ProfileState(
    val profile: UserProfile? = null,
    val isLoading: Boolean = true,
    val isUploadingImage: Boolean = false,
    val isUpdatingName: Boolean = false,
    val isEditingName: Boolean = false,
    val editNameInput: String = "",
    val isDarkTheme: Boolean = false
) : UiState

sealed interface ProfileEvent : UiEvent {
    object LogoutClicked : ProfileEvent
    data class AvatarSelected(val uri: Uri) : ProfileEvent
    object EditNameClicked : ProfileEvent
    data class NameInputChanged(val name: String) : ProfileEvent
    object SaveNameClicked : ProfileEvent
    data class ThemeToggled(val isDark: Boolean) : ProfileEvent
}

sealed interface ProfileEffect : UiEffect {
    object NavigateToAuth : ProfileEffect
    data class ShowError(val error: Throwable) : ProfileEffect
    data class ShowMessage(val message: String) : ProfileEffect
}