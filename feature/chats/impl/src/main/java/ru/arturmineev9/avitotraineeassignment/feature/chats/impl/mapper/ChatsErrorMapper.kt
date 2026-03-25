package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.mapper

import android.content.Context
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.exception.ChatsException

fun Throwable.toUiText(context: Context): String {
    return when (this) {
        is ChatsException.DatabaseError -> "Ошибка базы данных. Попробуйте перезапустить приложение."
        is ChatsException.DiskFull -> "Недостаточно места на устройстве."
        is ChatsException.ChatNotFound -> "Чат не найден или был удален."
        else -> this.message ?: "Произошла неизвестная ошибка"
    }
}
