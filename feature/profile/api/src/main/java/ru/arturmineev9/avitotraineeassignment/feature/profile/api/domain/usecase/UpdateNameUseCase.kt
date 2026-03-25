package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase

interface UpdateNameUseCase {
    suspend operator fun invoke(name: String) : Result<Unit>
}
