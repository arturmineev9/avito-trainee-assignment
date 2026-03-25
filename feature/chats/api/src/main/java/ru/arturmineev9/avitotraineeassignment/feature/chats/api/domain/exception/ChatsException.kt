package ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.exception

sealed class ChatsException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {
    class DatabaseError(cause: Throwable? = null) : ChatsException(cause = cause)
    class DiskFull(cause: Throwable? = null) : ChatsException(cause = cause)
    class ChatNotFound(cause: Throwable? = null) : ChatsException(cause = cause)
    class Unknown(message: String? = null, cause: Throwable? = null) : ChatsException(message, cause)
}
