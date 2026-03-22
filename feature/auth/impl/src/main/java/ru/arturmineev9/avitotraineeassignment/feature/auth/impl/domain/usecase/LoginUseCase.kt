package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase

import android.util.Patterns
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception.AuthException
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthUser> {
        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(AuthException.InvalidEmail())
        }
        if (password.length < 6) {
            return Result.failure(AuthException.WeakPassword())
        }
        return repository.login(email, password)
    }
}
