package ru.arturmineev9.avitotraineeassignment.feature.chat.api.data.datasource

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.core.database.entity.MessageEntity

interface LocalChatDataSource {
    fun getMessages(chatId: String): Flow<List<MessageEntity>>
    suspend fun saveMessage(message: MessageEntity)
}
