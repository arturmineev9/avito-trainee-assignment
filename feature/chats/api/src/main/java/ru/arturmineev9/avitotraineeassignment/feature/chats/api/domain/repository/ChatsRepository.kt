package ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat

interface ChatsRepository {
    fun getChatsPaged(): Flow<PagingData<Chat>>
    fun searchChatsPaged(query: String): Flow<PagingData<Chat>>
    suspend fun createNewChat(title: String): Result<String>
}
