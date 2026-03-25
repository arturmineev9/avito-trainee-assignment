package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.mapper

import android.content.Context
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception.AuthException
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.R

fun mapFirebaseException(e: Exception): AuthException {
    return when (e) {
        is FirebaseAuthInvalidUserException -> AuthException.UserNotFound(e)
        is FirebaseAuthInvalidCredentialsException -> AuthException.InvalidCredentials(e)
        is FirebaseAuthUserCollisionException -> AuthException.EmailAlreadyInUse(e)
        is FirebaseNetworkException -> AuthException.NetworkError(e)
        else -> AuthException.Unknown(e.message, e)
    }
}

fun Throwable.toUiText(context: Context): String {
    return when (this) {
        is AuthException.InvalidCredentials -> context.getString(R.string.error_invalid_credentials)
        is AuthException.UserNotFound -> context.getString(R.string.error_user_not_found)
        is AuthException.NetworkError -> context.getString(R.string.error_network)
        is AuthException.InvalidEmail -> context.getString(R.string.error_invalid_email)
        is AuthException.EmailAlreadyInUse -> context.getString(R.string.error_email_already_in_use)
        is AuthException.WeakPassword -> context.getString(R.string.error_weak_password)
        else -> this.message ?: context.getString(R.string.error_unknown)
    }
}
