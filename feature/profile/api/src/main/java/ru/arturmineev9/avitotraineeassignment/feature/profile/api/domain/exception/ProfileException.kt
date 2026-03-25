package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.exception

sealed class ProfileException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {
    class FileProcessingError(cause: Throwable? = null) : ProfileException(cause = cause)
    class NameUpdateError(cause: Throwable? = null) : ProfileException(cause = cause)
    class NetworkError(cause: Throwable? = null) : ProfileException(cause = cause)
    class LogoutError(cause: Throwable? = null) : ProfileException(cause = cause)
    class Unknown(message: String? = null, cause: Throwable? = null) : ProfileException(message, cause)
}
