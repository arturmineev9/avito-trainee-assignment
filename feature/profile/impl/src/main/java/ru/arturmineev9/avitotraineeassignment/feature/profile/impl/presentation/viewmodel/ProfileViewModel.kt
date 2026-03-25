package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.BaseViewModel
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.GetProfileUseCase
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.LogoutUseCase
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.ThemeUseCases
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.UpdateNameUseCase
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.UploadAvatarUseCase
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileEffect
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileEvent
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileState
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateNameUseCase: UpdateNameUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val themeUseCases: ThemeUseCases
) : BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(ProfileState()) {

    init {
        viewModelScope.launch {
            getProfileUseCase().collect { profile ->
                setState {
                    copy(
                        profile = profile,
                        isLoading = false,
                        editNameInput = profile.name
                    )
                }
            }
        }

        viewModelScope.launch {
            themeUseCases.getTheme().collect { isDark ->
                setState { copy(isDarkTheme = isDark) }
            }
        }
    }

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LogoutClicked -> performLogout()
            is ProfileEvent.AvatarSelected -> performUploadAvatar(event.uri)
            is ProfileEvent.EditNameClicked -> setState { copy(isEditingName = true) }
            is ProfileEvent.NameInputChanged -> setState { copy(editNameInput = event.name) }
            is ProfileEvent.SaveNameClicked -> performUpdateName()
            is ProfileEvent.ThemeToggled -> performToggleTheme(event.isDark)
        }
    }

    private fun performUploadAvatar(uri: Uri) {
        viewModelScope.launch {
            setState { copy(isUploadingImage = true) }
            uploadAvatarUseCase(uri).onFailure { error ->
                setEffect { ProfileEffect.ShowError(error) }
            }
            setState { copy(isUploadingImage = false) }
        }
    }

    private fun performUpdateName() {
        val newName = currentState.editNameInput
        if (newName.isBlank()) return

        viewModelScope.launch {
            setState { copy(isUpdatingName = true) }

            updateNameUseCase(newName).onSuccess {
                setState {
                    copy(
                        isEditingName = false,
                        isUpdatingName = false,
                        profile = currentState.profile?.copy(name = newName)
                    )
                }
                setEffect { ProfileEffect.ShowMessage("Имя успешно обновлено") }
            }.onFailure { error ->
                setState { copy(isUpdatingName = false) }
                setEffect { ProfileEffect.ShowError(error) }
            }
        }
    }

    private fun performToggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            themeUseCases.toggleTheme(isDark)
        }
    }

    private fun performLogout() {
        viewModelScope.launch {
            logoutUseCase().onSuccess {
                setEffect { ProfileEffect.NavigateToAuth }
            }.onFailure { error ->
                setEffect { ProfileEffect.ShowError(error) }
            }
        }
    }
}
