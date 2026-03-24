package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.model

data class UserProfile(
    val uid: String,
    val name: String,
    val email: String,
    val phone: String,
    val photoUrl: String?,
    val tokensCount: Int
)
