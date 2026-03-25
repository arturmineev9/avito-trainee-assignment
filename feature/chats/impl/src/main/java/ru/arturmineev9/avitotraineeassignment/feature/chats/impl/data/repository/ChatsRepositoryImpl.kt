package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.data.repository

import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.arturmineev9.avitotraineeassignment.core.database.dao.ChatDao
import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatEntity
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.exception.ChatsException
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.repository.ChatsRepository
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException
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
            pagingSourceFactory = chatDao::getChatsPaged
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun searchChatsPaged(query: String): Flow<PagingData<Chat>> {
        val ftsQuery = "$query*"

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { chatDao.searchChatsPaged(ftsQuery) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun createNewChat(title: String): Result<String> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val newChatId = UUID.randomUUID().toString()
                val currentTime = System.currentTimeMillis()

                val newChat = ChatEntity(
                    id = newChatId,
                    title = title,
                    createdAt = currentTime
                )

                chatDao.insertChat(newChat)
                Result.success(newChatId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: SQLiteDiskIOException) {
                Result.failure(ChatsException.DiskFull(e))
            } catch (e: SQLiteException) {
                Result.failure(ChatsException.DatabaseError(e))
            }
        }

    private fun ChatEntity.toDomain(): Chat {
        return Chat(
            id = this.id,
            title = this.title,
            createdAt = this.createdAt
        )
    }
}
