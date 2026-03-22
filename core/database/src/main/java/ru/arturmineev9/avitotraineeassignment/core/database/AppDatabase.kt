package ru.arturmineev9.avitotraineeassignment.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.arturmineev9.avitotraineeassignment.core.database.dao.ChatDao
import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatEntity
import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatFtsEntity
import ru.arturmineev9.avitotraineeassignment.core.database.entity.MessageEntity

@Database(
    entities = [
        ChatEntity::class,
        ChatFtsEntity::class,
        MessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val chatDao: ChatDao
    // abstract val messageDao: MessageDao
}
