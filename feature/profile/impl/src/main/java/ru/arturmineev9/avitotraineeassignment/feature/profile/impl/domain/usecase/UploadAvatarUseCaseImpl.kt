package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase

import android.net.Uri
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.UploadAvatarUseCase
import javax.inject.Inject

class UploadAvatarUseCaseImpl @Inject constructor(private val repository: ProfileRepository) : UploadAvatarUseCase {
    override suspend operator fun invoke(uri: Uri) = repository.uploadAvatar(uri)
}
