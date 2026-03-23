package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.mapper

import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.exception.ChatException

fun Throwable.toUiText(context: android.content.Context): String {
    return when (this) {
        is ChatException.NetworkError -> "Проверьте соединение с интернетом"
        is ChatException.AuthError -> "Ошибка авторизации GigaChat"
        is ChatException.DailyLimitReached -> "Лимит запросов исчерпан. Попробуйте позже"
        is ChatException.EmptyResponse -> "ИИ не смог ответить на этот вопрос"
        else -> this.message ?: "Неизвестная ошибка чата"
    }
}
