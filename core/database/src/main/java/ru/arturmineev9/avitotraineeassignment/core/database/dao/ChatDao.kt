package ru.arturmineev9.avitotraineeassignment.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.arturmineev9.avitotraineeassignment.core.database.entity.ChatEntity

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Query("SELECT * FROM chats ORDER BY createdAt DESC")
    fun getChatsPaged(): PagingSource<Int, ChatEntity>

    @Query("""
        SELECT chats.* 
        FROM chats 
        JOIN chats_fts ON chats.id = chats_fts.rowid 
        WHERE chats_fts MATCH :searchQuery 
        ORDER BY chats.createdAt DESC
    """)
    fun searchChatsPaged(searchQuery: String): PagingSource<Int, ChatEntity>
}
