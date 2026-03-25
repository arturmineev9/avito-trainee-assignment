package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.mapper

import android.content.Context
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.exception.ProfileException

fun Throwable.toUiText(context: Context): String {
    return when (this) {
        is ProfileException.FileProcessingError -> "Ошибка при обработке изображения"
        is ProfileException.NameUpdateError -> "Не удалось обновить имя пользователя"
        is ProfileException.NetworkError -> "Ошибка сети. Проверьте соединение"
        is ProfileException.LogoutError -> "Не удалось выйти из аккаунта"
        else -> this.message ?: "Произошла ошибка в профиле"
    }
}
