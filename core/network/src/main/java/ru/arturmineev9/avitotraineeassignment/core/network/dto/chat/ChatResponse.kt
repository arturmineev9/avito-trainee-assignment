package ru.arturmineev9.avitotraineeassignment.core.network.dto.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    @SerialName("choices") val choices: List<ChoiceDto>,
    @SerialName("created") val created: Long,
    @SerialName("model") val model: String,
    @SerialName("object") val obj: String,
    @SerialName("usage") val usage: UsageDto
)
