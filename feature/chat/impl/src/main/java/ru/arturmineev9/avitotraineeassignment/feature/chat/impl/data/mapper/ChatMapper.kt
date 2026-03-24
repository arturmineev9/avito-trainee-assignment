package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.mapper

import ru.arturmineev9.avitotraineeassignment.core.database.entity.MessageEntity
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model.Message

fun MessageEntity.toDomain(): Message {
    return Message(
        id = this.id,
        text = this.text,
        isFromUser = this.isFromUser,
        createdAt = this.createdAt
    )
}
