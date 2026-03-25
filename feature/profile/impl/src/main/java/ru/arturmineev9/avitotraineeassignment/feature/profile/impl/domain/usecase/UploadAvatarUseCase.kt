package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase

import android.net.Uri
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(uri: Uri) = repository.uploadAvatar(uri)
}
