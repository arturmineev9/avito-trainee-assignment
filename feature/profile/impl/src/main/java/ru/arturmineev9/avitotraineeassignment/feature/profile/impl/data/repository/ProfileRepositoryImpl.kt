package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.data.repository

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.arturmineev9.avitotraineeassignment.core.common.datastore.settings.SettingsManager
import ru.arturmineev9.avitotraineeassignment.core.common.datastore.userbalance.UserBalanceManager
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.exception.ProfileException
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.model.UserProfile
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.repository.ProfileRepository
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.data.datasource.ProfileFileDataSource
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val settingsManager: SettingsManager,
    private val userBalanceManager: UserBalanceManager,
    private val fileDataSource: ProfileFileDataSource
) : ProfileRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserProfile(): Flow<UserProfile> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
                trySend(user)
            }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }.flatMapLatest { firebaseUser ->
        userBalanceManager.observeUserTokens(firebaseUser.uid).map { tokens ->
            UserProfile(
                uid = firebaseUser.uid,
                name = firebaseUser.displayName ?: "Имя не указано",
                email = firebaseUser.email ?: "",
                photoUrl = null,
                tokensCount = tokens
            )
        }
    }

    override suspend fun uploadAvatar(imageUri: Uri): Result<String> = withContext(Dispatchers.IO) {
        try {
            val user = firebaseAuth.currentUser
                ?: return@withContext Result.failure(ProfileException.Unknown("Пользователь не авторизован"))

            val localPath = fileDataSource.saveAvatar(imageUri, user.uid)

            settingsManager.saveAvatarPath(user.uid, localPath)

            Result.success(localPath)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Result.failure(ProfileException.FileProcessingError(e))
        } catch (e: IllegalStateException) {
            Result.failure(ProfileException.FileProcessingError(e))
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
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseNetworkException) {
            Result.failure(ProfileException.NetworkError(e))
        } catch (e: FirebaseException) {
            Result.failure(ProfileException.NameUpdateError(e))
        } catch (e: IllegalStateException) {
            Result.failure(ProfileException.NameUpdateError(e))
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: IllegalStateException) {
            Result.failure(ProfileException.LogoutError(e))
        } catch (e: FirebaseException) {
            Result.failure(ProfileException.LogoutError(e))
        } catch (e: FirebaseNetworkException) {
            Result.failure(ProfileException.NetworkError(e))
        }
    }
}
