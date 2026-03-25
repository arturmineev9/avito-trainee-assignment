package ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository

import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser

interface AuthRepository {
    suspend fun getCurrentUser(): AuthUser?
    suspend fun login(email: String, password: String): Result<AuthUser>
    suspend fun register(email: String, password: String): Result<AuthUser>
    suspend fun logout()
    suspend fun loginWithGoogle(idToken: String): Result<AuthUser>
}
