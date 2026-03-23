package ru.arturmineev9.avitotraineeassignment.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String,
    val title: String,
    val createdAt: Long,
    @ColumnInfo(name = "rowid")
    val rowId: Long = 0
)
