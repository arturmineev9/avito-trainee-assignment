package ru.arturmineev9.avitotraineeassignment.feature.chats.api.data.datasource

import androidx.paging.PagingSource
import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatEntity

interface LocalChatsDataSource {
    fun getChatsPaged(): PagingSource<Int, ChatEntity>
    fun searchChatsPaged(searchQuery: String): PagingSource<Int, ChatEntity>
    suspend fun insertChat(chat: ChatEntity)
}
