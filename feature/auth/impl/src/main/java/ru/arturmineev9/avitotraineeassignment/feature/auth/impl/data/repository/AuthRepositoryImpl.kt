package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception.AuthException
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository.AuthRepository
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.mapper.mapFirebaseException
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.mapper.toDomain
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
        } catch (e: Exception) {
            Result.failure(mapFirebaseException(e))
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
        } catch (e: Exception) {
            Result.failure(mapFirebaseException(e))
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}
