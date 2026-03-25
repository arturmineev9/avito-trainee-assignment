package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.core.common.datastore.settings.SettingsManager
import javax.inject.Inject

class ThemeUseCases @Inject constructor(private val settingsManager: SettingsManager) {
    fun getTheme(): Flow<Boolean> = settingsManager.isDarkTheme
    suspend fun toggleTheme(isDark: Boolean) = settingsManager.setDarkTheme(isDark)
}
