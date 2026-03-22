package ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation

import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiEffect
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiEvent
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiState

data class AuthState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val isLoading: Boolean = false,
    val isLoginMode: Boolean = true
) : UiState

sealed interface AuthEvent : UiEvent {
    data class EmailChanged(val email: String) : AuthEvent
    data class PasswordChanged(val password: String) : AuthEvent
    object ToggleAuthModeClicked : AuthEvent
    object SubmitClicked : AuthEvent
}

sealed interface AuthEffect : UiEffect {
    object NavigateToChats : AuthEffect
    data class ShowError(val error: Throwable) : AuthEffect
}
