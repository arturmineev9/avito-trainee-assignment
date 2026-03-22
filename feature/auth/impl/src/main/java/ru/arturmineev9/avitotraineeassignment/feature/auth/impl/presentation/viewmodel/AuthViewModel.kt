package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.BaseViewModel
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEffect
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthEvent
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.presentation.AuthState
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase.LoginUseCase
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>(AuthState()) {

    override fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> setState { copy(emailInput = event.email) }
            is AuthEvent.PasswordChanged -> setState { copy(passwordInput = event.password) }
            is AuthEvent.ToggleAuthModeClicked -> setState { copy(isLoginMode = !isLoginMode) }
            is AuthEvent.SubmitClicked -> performSubmit()
        }
    }

    private fun performSubmit() {
        val email = currentState.emailInput
        val password = currentState.passwordInput

        viewModelScope.launch {
            setState { copy(isLoading = true) }

            val result = if (currentState.isLoginMode) {
                loginUseCase(email, password)
            } else {
                registerUseCase(email, password)
            }

            result.onSuccess {
                setEffect { AuthEffect.NavigateToChats }
            }.onFailure { error ->
                setEffect { AuthEffect.ShowError(error) }
            }

            setState { copy(isLoading = false) }
        }
    }
}
