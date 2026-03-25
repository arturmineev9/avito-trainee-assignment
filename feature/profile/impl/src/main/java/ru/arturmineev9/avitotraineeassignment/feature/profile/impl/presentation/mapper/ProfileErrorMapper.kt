package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.mapper

import android.content.Context
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.exception.ProfileException
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.R
fun Throwable.toUiText(context: Context): String {
    return when (this) {
        is ProfileException.FileProcessingError -> context.getString(R.string.error_file_processing)
        is ProfileException.NameUpdateError -> context.getString(R.string.error_name_update)
        is ProfileException.NetworkError -> context.getString(R.string.error_network)
        is ProfileException.LogoutError -> context.getString(R.string.error_logout)
        else -> this.message ?: context.getString(R.string.error_unknown)
    }
}
