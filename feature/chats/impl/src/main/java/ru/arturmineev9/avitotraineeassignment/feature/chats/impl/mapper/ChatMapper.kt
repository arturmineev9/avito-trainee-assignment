package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.mapper

import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatEntity
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat

fun ChatEntity.toDomain(): Chat {
    return Chat(
        id = this.id,
        title = this.title,
        createdAt = this.createdAt
    )
}