package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception.AuthException
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository.AuthRepository
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.usecase.LoginWithGoogleUseCase
import javax.inject.Inject

class LoginWithGoogleUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : LoginWithGoogleUseCase {
    override suspend operator fun invoke(idToken: String): Result<AuthUser> {
        if (idToken.isBlank()) {
            return Result.failure(AuthException.Unknown("Google ID Token is empty"))
        }

        return repository.loginWithGoogle(idToken)
    }
}
