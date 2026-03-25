package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.data.repository

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception.AuthException
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository.AuthRepository
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.mapper.mapFirebaseException
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.mapper.toDomain
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun getCurrentUser(): AuthUser? {
        return firebaseAuth.currentUser?.toDomain()
    }

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                Result.success(user.toDomain())
            } else {
                Result.failure(AuthException.UserNotFound())
            }
        } catch (e: FirebaseException) {
            Result.failure(mapFirebaseException(e))
        } catch (e: IOException) {
            Result.failure(AuthException.NetworkError(e))
        }
    }

    override suspend fun register(email: String, password: String): Result<AuthUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                Result.success(user.toDomain())
            } else {
                Result.failure(AuthException.Unknown("User is null after registration"))
            }
        } catch (e: FirebaseException) {
            Result.failure(mapFirebaseException(e))
        } catch (e: IOException) {
            Result.failure(AuthException.NetworkError(e))
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun loginWithGoogle(idToken: String): Result<AuthUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val user = result.user ?: throw AuthException.UserNotFound()
            Result.success(user.toDomain())
        } catch (e: FirebaseException) {
            Result.failure(mapFirebaseException(e))
        } catch (e: IOException) {
            Result.failure(AuthException.NetworkError(e))
        }
    }
}
