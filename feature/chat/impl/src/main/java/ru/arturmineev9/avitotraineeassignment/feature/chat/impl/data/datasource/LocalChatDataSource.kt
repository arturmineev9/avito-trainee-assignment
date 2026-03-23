package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.core.database.dao.MessageDao
import ru.arturmineev9.avitotraineeassignment.core.database.entity.MessageEntity
import javax.inject.Inject

class LocalChatDataSource @Inject constructor(
    private val messageDao: MessageDao
) {
    fun getMessages(chatId: String): Flow<List<MessageEntity>> {
        return messageDao.getMessagesByChatId(chatId)
    }

    suspend fun saveMessage(message: MessageEntity) {
        messageDao.insertMessage(message)
    }
}
