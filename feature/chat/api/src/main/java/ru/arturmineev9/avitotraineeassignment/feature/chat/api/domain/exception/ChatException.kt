package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.exception

sealed class ChatException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {
    class NetworkError(cause: Throwable? = null) : ChatException(cause = cause)
    class AuthError(cause: Throwable? = null) : ChatException(cause = cause)
    class DailyLimitReached(cause: Throwable? = null) : ChatException(cause = cause)
    class EmptyResponse(cause: Throwable? = null) : ChatException(cause = cause)
    class OutOfTokens(cause: Throwable? = null) : ChatException("Недостаточно токенов", cause = cause)
    class Unknown(message: String? = null, cause: Throwable? = null) : ChatException(message, cause)
}
