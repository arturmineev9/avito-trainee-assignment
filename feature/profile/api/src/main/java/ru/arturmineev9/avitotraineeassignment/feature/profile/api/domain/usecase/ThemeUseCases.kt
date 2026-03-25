package ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ThemeUseCases {
    fun getTheme(): Flow<Boolean>
    suspend fun toggleTheme(isDark: Boolean): Unit
}
