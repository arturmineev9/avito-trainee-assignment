package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke() = repository.logout()
}
