package ru.arturmineev9.avitotraineeassignment.feature.chat.api.data.datasource

import ru.arturmineev9.avitotraineeassignment.core.network.dto.auth.TokenResponse
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.ChatRequest
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.ChatResponse

interface RemoteChatDataSource {
    suspend fun getAccessToken(authKey: String): TokenResponse
    suspend fun sendPrompt(request: ChatRequest): ChatResponse
}
