package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.UpdateNameUseCase
import javax.inject.Inject

class UpdateNameUseCaseImpl @Inject constructor(private val repository: ProfileRepository) : UpdateNameUseCase {
    override suspend operator fun invoke(name: String) = repository.updateName(name)
}
