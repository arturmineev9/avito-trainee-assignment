package ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model

sealed class ChatsException : Throwable() {
    class DatabaseError : ChatsException()
    class DiskFull : ChatsException()
    class ChatNotFound : ChatsException()
    class Unknown(override val message: String? = null) : ChatsException()
}
