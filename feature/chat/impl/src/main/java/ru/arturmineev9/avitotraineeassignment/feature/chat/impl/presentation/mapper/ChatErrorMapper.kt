package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.mapper

import android.content.Context
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.exception.ChatException
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.R

fun Throwable.toUiText(context: Context): String {
    return when (this) {
        is ChatException.NetworkError -> context.getString(R.string.error_chat_network)
        is ChatException.AuthError -> context.getString(R.string.error_chat_auth)
        is ChatException.DailyLimitReached -> context.getString(R.string.error_chat_limit_reached)
        is ChatException.EmptyResponse -> context.getString(R.string.error_chat_empty_response)
        else -> this.message ?: context.getString(R.string.error_chat_unknown)
    }
}
