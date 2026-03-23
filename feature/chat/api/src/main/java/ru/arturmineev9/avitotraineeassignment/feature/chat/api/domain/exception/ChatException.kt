package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.exception

sealed class ChatException : Throwable() {
    class NetworkError : ChatException()
    class AuthError : ChatException()
    class DailyLimitReached : ChatException()
    class EmptyResponse : ChatException()
    class Unknown(override val message: String? = null) : ChatException()
}
