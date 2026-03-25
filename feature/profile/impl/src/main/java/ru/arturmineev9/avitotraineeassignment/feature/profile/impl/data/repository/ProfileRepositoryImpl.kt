package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.data.repository

import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.exception.ProfileException
import android.net.Uri
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.arturmineev9.avitotraineeassignment.core.common.settings.SettingsManager
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.model.UserProfile
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.data.datasource.ProfileFileDataSource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val settingsManager: SettingsManager,
    private val fileDataSource: ProfileFileDataSource
) : ProfileRepository {

    override fun getUserProfile(): Flow<UserProfile> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
                val profile = UserProfile(
                    uid = user.uid,
                    name = user.displayName ?: "Имя не указано",
                    email = user.email ?: "",
                    photoUrl = null,
                    tokensCount = 500
                )
                trySend(profile)
            }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override suspend fun uploadAvatar(imageUri: Uri): Result<String> = withContext(Dispatchers.IO) {
        try {
            val user = firebaseAuth.currentUser
                ?: return@withContext Result.failure(ProfileException.Unknown("Пользователь не авторизован"))

            val localPath = fileDataSource.saveAvatar(imageUri, user.uid)

            settingsManager.saveAvatarPath(user.uid, localPath)

            Result.success(localPath)
        } catch (e: Exception) {
            Result.failure(mapToProfileException(e))
        }
    }

    override suspend fun updateName(newName: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val user = firebaseAuth.currentUser
                ?: return@withContext Result.failure(ProfileException.Unknown("Пользователь не авторизован"))

            val profileUpdates = userProfileChangeRequest {
                displayName = newName
            }
            user.updateProfile(profileUpdates).await()
            user.reload().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(mapToProfileException(e))
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(ProfileException.LogoutError())
        }
    }

    private fun mapToProfileException(e: Exception): ProfileException {
        return when (e) {
            is java.io.IOException -> ProfileException.FileProcessingError()
            is FirebaseNetworkException -> ProfileException.NetworkError()
            is IllegalStateException -> ProfileException.FileProcessingError()
            else -> ProfileException.Unknown(e.message)
        }
    }
}
