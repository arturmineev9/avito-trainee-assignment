package ru.arturmineev9.avitotraineeassignment.core.network.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.ChatRequest
import ru.arturmineev9.avitotraineeassignment.core.network.dto.chat.ChatResponse

interface GigaChatApi {
    @POST("api/v1/chat/completions")
    suspend fun sendPrompt(
        @Body request: ChatRequest
    ) : ChatResponse
}