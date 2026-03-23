package ru.arturmineev9.avitotraineeassignment.core.database.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.FtsOptions

@Fts4(
    contentEntity = ChatEntity::class,
    tokenizer = FtsOptions.TOKENIZER_UNICODE61
)
@Entity(tableName = "chats_fts")
data class ChatFtsEntity(
    val title: String
)
