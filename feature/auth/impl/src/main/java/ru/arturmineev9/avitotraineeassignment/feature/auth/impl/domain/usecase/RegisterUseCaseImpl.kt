package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase

import android.util.Patterns
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception.AuthException
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository.AuthRepository
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : RegisterUseCase {

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }

    override suspend operator fun invoke(email: String, password: String): Result<AuthUser> {
        val isValidEmail = email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isValidPassword = password.length >= MIN_PASSWORD_LENGTH

        return when {
            !isValidEmail -> Result.failure(AuthException.InvalidEmail())
            !isValidPassword -> Result.failure(AuthException.WeakPassword())
            else -> repository.register(email, password)
        }
    }
}

