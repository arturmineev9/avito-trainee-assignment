package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.LogoutUseCase
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(private val repository: ProfileRepository) : LogoutUseCase {
    override suspend operator fun invoke() = repository.logout()
}
