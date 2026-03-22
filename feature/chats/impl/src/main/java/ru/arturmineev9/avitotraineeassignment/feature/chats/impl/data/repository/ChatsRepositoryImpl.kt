package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.arturmineev9.avitotraineeassignment.core.database.dao.ChatDao
import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatEntity
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.repository.ChatsRepository
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.mapper.toDomain
import java.util.UUID
import javax.inject.Inject

class ChatsRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao
) : ChatsRepository {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        enablePlaceholders = false,
        initialLoadSize = 20
    )

    override fun getChatsPaged(): Flow<PagingData<Chat>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { chatDao.getChatsPaged() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun searchChatsPaged(query: String): Flow<PagingData<Chat>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { chatDao.searchChatsPaged("*$query*") }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun createNewChat(title: String): String {
        val newChatId = UUID.randomUUID().toString()
        val currentTime = System.currentTimeMillis()

        val newChat = ChatEntity(
            id = newChatId,
            title = title,
            createdAt = currentTime
        )

        chatDao.insertChat(newChat)
        return newChatId
    }
}
