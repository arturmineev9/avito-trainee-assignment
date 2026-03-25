package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase

import android.net.Uri

interface UploadAvatarUseCase {
    suspend operator fun invoke(uri: Uri) : Result<String>
}
