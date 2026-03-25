package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.model.UserProfile

interface ProfileRepository {
    fun getUserProfile(): Flow<UserProfile>
    suspend fun uploadAvatar(imageUri: Uri): Result<String>
    suspend fun updateName(newName: String): Result<Unit>
    suspend fun logout(): Result<Unit>
}
