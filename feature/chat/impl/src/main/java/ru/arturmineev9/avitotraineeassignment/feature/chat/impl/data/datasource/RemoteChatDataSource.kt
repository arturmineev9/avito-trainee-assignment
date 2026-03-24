package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource

import ru.arturmineev9.avitotraineeassignment.core.network.api.GigaChatApi
import ru.arturmineev9.avitotraineeassignment.core.network.api.GigaChatAuthApi
import ru.arturmineev9.avitotraineeassignment.core.network.dto.auth.TokenResponse
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.ChatRequest
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.ChatResponse
import java.util.UUID
import javax.inject.Inject

class RemoteChatDataSource @Inject constructor(
    private val authApi: GigaChatAuthApi,
    private val chatApi: GigaChatApi
    ) {
    suspend fun getAccessToken(authKey: String): TokenResponse {
        val rqUid = UUID.randomUUID().toString()
        return authApi.getAccessToken(authorization = "Basic $authKey", rqUid = rqUid)
    }
    suspend fun sendPrompt(request: ChatRequest): ChatResponse {
        return chatApi.sendPrompt(request)
    }
}
