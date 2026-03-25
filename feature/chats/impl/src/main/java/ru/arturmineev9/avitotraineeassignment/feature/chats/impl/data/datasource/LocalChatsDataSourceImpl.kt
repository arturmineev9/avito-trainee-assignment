package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.data.datasource

import androidx.paging.PagingSource
import ru.arturmineev9.avitotraineeassignment.core.database.dao.ChatDao
import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatEntity
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.data.datasource.LocalChatsDataSource
import javax.inject.Inject

class LocalChatsDataSourceImpl @Inject constructor(
    private val chatDao: ChatDao
) : LocalChatsDataSource {

    override fun getChatsPaged(): PagingSource<Int, ChatEntity> {
        return chatDao.getChatsPaged()
    }

    override fun searchChatsPaged(searchQuery: String): PagingSource<Int, ChatEntity> {
        if (searchQuery.isBlank()) {
            return chatDao.getChatsPaged()
        }

        return chatDao.searchChatsPaged(searchQuery)
    }

    override suspend fun insertChat(chat: ChatEntity) {
        chatDao.insertChat(chat)
    }
}
