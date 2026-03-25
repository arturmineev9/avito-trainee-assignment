package ru.arturmineev9.avitotraineeassignment.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatEntity

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Query("SELECT * FROM chats ORDER BY createdAt DESC")
    fun getChatsPaged(): PagingSource<Int, ChatEntity>

    @Query("SELECT * FROM chats WHERE title LIKE '%' || :searchQuery || '%' ORDER BY createdAt DESC")
    fun searchChatsPaged(searchQuery: String): PagingSource<Int, ChatEntity>

    @Query("SELECT title FROM chats WHERE id = :chatId")
    fun getChatTitle(chatId: String): Flow<String>

    @Query("UPDATE chats SET title = :title WHERE id = :chatId")
    suspend fun updateChatTitle(chatId: String, title: String)
}
