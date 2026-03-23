package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model.Message

interface ChatRepository {
    fun getMessages(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(chatId: String, text: String) : Result<Unit>
}
