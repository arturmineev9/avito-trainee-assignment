package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model

data class Message(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val createdAt: Long
)
