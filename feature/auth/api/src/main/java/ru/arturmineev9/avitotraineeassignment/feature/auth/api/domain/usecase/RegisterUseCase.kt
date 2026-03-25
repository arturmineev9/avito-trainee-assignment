package ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser

interface RegisterUseCase {
    suspend operator fun invoke(email: String, password: String): Result<AuthUser>
}
