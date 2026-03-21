package ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model

data class AuthUser(
    val uid: String,
    val email: String,
    val displayName: String? = null
)
