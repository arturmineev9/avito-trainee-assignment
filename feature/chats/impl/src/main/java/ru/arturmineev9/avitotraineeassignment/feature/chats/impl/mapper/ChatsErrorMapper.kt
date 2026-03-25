package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.mapper

import android.content.Context
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.exception.ChatsException
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.R // Импорт R из модуля chats

fun Throwable.toUiText(context: Context): String {
    return when (this) {
        is ChatsException.DatabaseError -> context.getString(R.string.error_chats_database)
        is ChatsException.DiskFull -> context.getString(R.string.error_chats_disk_full)
        is ChatsException.ChatNotFound -> context.getString(R.string.error_chats_not_found)
        else -> this.message ?: context.getString(R.string.error_chats_unknown)
    }
}
