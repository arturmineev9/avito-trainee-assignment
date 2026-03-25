package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.model.UserProfile

interface GetProfileUseCase {
    operator fun invoke(): Flow<UserProfile>
}
