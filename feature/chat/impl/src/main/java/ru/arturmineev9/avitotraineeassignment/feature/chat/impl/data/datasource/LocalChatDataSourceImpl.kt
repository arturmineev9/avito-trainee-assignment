package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.core.database.dao.MessageDao
import ru.arturmineev9.avitotraineeassignment.core.database.entity.MessageEntity
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.data.datasource.LocalChatDataSource
import javax.inject.Inject

class LocalChatDataSourceImpl @Inject constructor(
    private val messageDao: MessageDao
) : LocalChatDataSource {

    override fun getMessages(chatId: String): Flow<List<MessageEntity>> {
        return messageDao.getMessagesByChatId(chatId)
    }

    override suspend fun saveMessage(message: MessageEntity) {
        messageDao.insertMessage(message)
    }
}
