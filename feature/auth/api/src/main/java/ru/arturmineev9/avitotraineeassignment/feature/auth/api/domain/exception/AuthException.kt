package ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception

sealed class AuthException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {
    class InvalidCredentials(cause: Throwable? = null) : AuthException(cause = cause)
    class UserNotFound(cause: Throwable? = null) : AuthException(cause = cause)
    class NetworkError(cause: Throwable? = null) : AuthException(cause = cause)
    class EmailAlreadyInUse(cause: Throwable? = null) : AuthException(cause = cause)
    class InvalidEmail(cause: Throwable? = null) : AuthException(cause = cause)
    class WeakPassword(cause: Throwable? = null) : AuthException(cause = cause)
    class Unknown(message: String? = null, cause: Throwable? = null) : AuthException(message, cause)
}
