package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception.AuthException
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository.AuthRepository
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<AuthUser> {
        if (idToken.isBlank()) {
            return Result.failure(AuthException.Unknown("Google ID Token is empty"))
        }

        return repository.loginWithGoogle(idToken)
    }
}
