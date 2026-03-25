package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.repository

import android.database.SQLException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.arturmineev9.avitotraineeassignment.core.common.datastore.userbalance.UserBalanceManager
import ru.arturmineev9.avitotraineeassignment.core.database.dao.ChatDao
import ru.arturmineev9.avitotraineeassignment.core.database.entity.MessageEntity
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.ChatRequest
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.ChatResponse
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.MessageDto
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.exception.ChatException
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model.Message
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository.ChatRepository
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource.LocalChatDataSource
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource.RemoteChatDataSource
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.mapper.toDomain
import java.io.IOException
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException
import javax.inject.Inject
import retrofit2.HttpException

class ChatRepositoryImpl @Inject constructor(
    private val localDataSource: LocalChatDataSource,
    private val remoteDataSource: RemoteChatDataSource,
    private val userBalanceManager: UserBalanceManager,
    private val firebaseAuth: FirebaseAuth,
    private val chatDao: ChatDao
) : ChatRepository {

    companion object {
        private const val MAX_HISTORY_MESSAGES = 20
        private const val HTTP_UNAUTHORIZED = 401
        private const val HTTP_TOO_MANY_REQUESTS = 429
    }

    override fun getMessages(chatId: String): Flow<List<Message>> {
        return localDataSource.getMessages(chatId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(chatId: String, text: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val uid = checkNotNull(firebaseAuth.currentUser?.uid) { "Пользователь не авторизован" }

                saveMessageLocally(chatId = chatId, text = text, isFromUser = true)

                val chatRequest = buildChatRequest(chatId)
                val response = fetchAiResponse(chatRequest)

                val aiResponseText = checkNotNull(response.choices.firstOrNull()?.message?.content) { "Пустой ответ от ИИ" }

                saveMessageLocally(chatId = chatId, text = aiResponseText, isFromUser = false)
                val usedTokens = response.usage.totalTokens
                userBalanceManager.spendTokens(uid, usedTokens)

                Result.success(Unit)
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                val mappedError = when (e.code()) {
                    HTTP_UNAUTHORIZED -> ChatException.AuthError(e)
                    HTTP_TOO_MANY_REQUESTS -> ChatException.DailyLimitReached(e)
                    else -> ChatException.Unknown(e.message(), e)
                }
                Result.failure(mappedError)
            } catch (e: IOException) {
                Result.failure(ChatException.NetworkError(e))
            } catch (e: IllegalStateException) {
                Result.failure(ChatException.Unknown(e.message, e))
            } catch (e: SQLException) {
                Result.failure(ChatException.Unknown("Ошибка базы данных", e))
            }
        }

    override fun getChatTitle(chatId: String): Flow<String> {
        return chatDao.getChatTitle(chatId)
    }

    override suspend fun updateChatTitle(chatId: String, title: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                chatDao.updateChatTitle(chatId, title)
                Result.success(Unit)
            } catch (e: CancellationException) {
                throw e
            } catch (e: SQLException) {
                Result.failure(ChatException.Unknown("Ошибка базы данных", e))
            }
        }

    private suspend fun saveMessageLocally(chatId: String, text: String, isFromUser: Boolean) {
        val entity = MessageEntity(
            id = UUID.randomUUID().toString(),
            chatId = chatId,
            text = text,
            isFromUser = isFromUser,
            createdAt = System.currentTimeMillis()
        )
        localDataSource.saveMessage(entity)
    }

    private suspend fun buildChatRequest(chatId: String): ChatRequest {
        val historyEntities = localDataSource.getMessages(chatId).first().takeLast(MAX_HISTORY_MESSAGES)

        val chatHistoryDtos = historyEntities.map { entity ->
            MessageDto(
                role = if (entity.isFromUser) "user" else "assistant",
                content = entity.text
            )
        }

        val systemMessage = MessageDto(
            role = "system",
            content = "Ты полезный и дружелюбный ИИ-ассистент."
        )

        return ChatRequest(messages = listOf(systemMessage) + chatHistoryDtos)
    }

    private suspend fun fetchAiResponse(request: ChatRequest): ChatResponse {
        return remoteDataSource.sendPrompt(request)
    }
}
