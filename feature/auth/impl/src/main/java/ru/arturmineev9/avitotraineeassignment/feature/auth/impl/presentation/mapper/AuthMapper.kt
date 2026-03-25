package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.mapper

import com.google.firebase.auth.FirebaseUser
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.model.AuthUser

fun FirebaseUser.toDomain(): AuthUser {
    return AuthUser(
        uid = this.uid,
        email = this.email ?: "",
        displayName = this.displayName
    )
}
