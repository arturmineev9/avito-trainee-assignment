package ru.arturmineev9.avitotraineeassignment.core.database.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = ChatEntity::class)
@Entity(tableName = "chats_fts")
data class ChatFtsEntity(
    val title: String
)
