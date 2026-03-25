package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.exception

sealed class ProfileException : Throwable() {
    class FileProcessingError : ProfileException()
    class NameUpdateError : ProfileException()
    class NetworkError : ProfileException()
    class LogoutError : ProfileException()
    class Unknown(override val message: String? = null) : ProfileException()
}
