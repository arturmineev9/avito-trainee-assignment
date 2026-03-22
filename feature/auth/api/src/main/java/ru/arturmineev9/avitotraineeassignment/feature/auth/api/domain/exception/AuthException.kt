package ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception

sealed class AuthException : Throwable() {
    class InvalidCredentials : AuthException()
    class UserNotFound : AuthException()
    class NetworkError : AuthException()
    class EmailAlreadyInUse : AuthException()
    class InvalidEmail : AuthException()
    class WeakPassword : AuthException()
    class Unknown(override val message: String? = null) : AuthException()
}
