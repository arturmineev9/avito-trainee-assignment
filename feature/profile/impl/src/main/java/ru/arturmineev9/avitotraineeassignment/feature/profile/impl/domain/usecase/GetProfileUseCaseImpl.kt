package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.arturmineev9.avitotraineeassignment.core.common.datastore.settings.SettingsManager
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.model.UserProfile
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.GetProfileUseCase
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository,
    private val settingsManager: SettingsManager
) : GetProfileUseCase {
    @OptIn(ExperimentalCoroutinesApi::class)
    override operator fun invoke(): Flow<UserProfile> {
        return repository.getUserProfile().flatMapLatest { user ->
            settingsManager.getAvatarPath(user.uid).map { localPath ->
                user.copy(photoUrl = localPath)
            }
        }
    }
}
