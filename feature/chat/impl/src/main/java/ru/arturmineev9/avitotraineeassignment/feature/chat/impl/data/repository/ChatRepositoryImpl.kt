package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.repository

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
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model.Message
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository.ChatRepository
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource.LocalChatDataSource
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource.RemoteChatDataSource
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase.mapper.toDomain
import java.util.UUID
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val localDataSource: LocalChatDataSource,
    private val remoteDataSource: RemoteChatDataSource,
    private val userBalanceManager: UserBalanceManager,
    private val firebaseAuth: FirebaseAuth,
    private val chatDao: ChatDao
) : ChatRepository {

    override fun getMessages(chatId: String): Flow<List<Message>> {
        return localDataSource.getMessages(chatId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(chatId: String, text: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val uid = firebaseAuth.currentUser?.uid
                    ?: throw IllegalStateException("Пользователь не авторизован")

                saveMessageLocally(chatId = chatId, text = text, isFromUser = true)

                val chatRequest = buildChatRequest(chatId)

                val response = fetchAiResponse(chatRequest)

                val aiResponseText = response.choices.firstOrNull()?.message?.content
                    ?: throw IllegalStateException("Пустой ответ от ИИ")


                saveMessageLocally(chatId = chatId, text = aiResponseText, isFromUser = false)
                val usedTokens = response.usage.totalTokens
                userBalanceManager.spendTokens(uid, usedTokens)

                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
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
            } catch (e: Exception) {
                Result.failure(e)
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
        val historyEntities = localDataSource.getMessages(chatId).first().takeLast(20)

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
