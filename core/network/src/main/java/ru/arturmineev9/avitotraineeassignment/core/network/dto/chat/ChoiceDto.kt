package ru.arturmineev9.avitotraineeassignment.core.network.dto.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChoiceDto(
    @SerialName("message") val message: MessageDto,
    @SerialName("index") val index: Int,
    @SerialName("finish_reason") val finishReason: String
)
