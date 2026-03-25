package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model.Message

interface ChatRepository {
    fun getMessages(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(chatId: String, text: String) : Result<Unit>
    fun getChatTitle(chatId: String): Flow<String>
    suspend fun updateChatTitle(chatId: String, title: String): Result<Unit>
}
