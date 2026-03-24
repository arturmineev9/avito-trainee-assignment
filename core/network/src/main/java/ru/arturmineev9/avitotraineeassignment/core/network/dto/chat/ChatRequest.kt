package ru.arturmineev9.avitotraineeassignment.core.network.dto.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    @SerialName("model") val model: String = "GigaChat",
    @SerialName("messages") val messages: List<MessageDto>,
    @SerialName("temperature") val temperature: Double = 0.87,
    @SerialName("top_p") val topP: Double = 0.47,
    @SerialName("n") val n: Int = 1,
    @SerialName("stream") val stream: Boolean = false,
    @SerialName("max_tokens") val maxTokens: Int = 512,
    @SerialName("repetition_penalty") val repetitionPenalty: Double = 1.07
)
