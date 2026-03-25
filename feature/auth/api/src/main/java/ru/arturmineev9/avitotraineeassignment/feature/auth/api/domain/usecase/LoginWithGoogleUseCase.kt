package ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser

interface LoginWithGoogleUseCase {
    suspend operator fun invoke(idToken: String): Result<AuthUser>
}
