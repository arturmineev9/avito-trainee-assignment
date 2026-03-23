package ru.arturmineev9.avitotraineeassignment.core.network.dto.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String
)
