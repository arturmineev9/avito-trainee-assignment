package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase

interface LogoutUseCase {
    suspend operator fun invoke() : Result<Unit>
}
