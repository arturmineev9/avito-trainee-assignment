package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.core.common.datastore.settings.SettingsManager
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.domain.usecase.ThemeUseCases
import javax.inject.Inject

class ThemeUseCasesImpl @Inject constructor(private val settingsManager: SettingsManager) : ThemeUseCases {
    override fun getTheme(): Flow<Boolean> = settingsManager.isDarkTheme
    override suspend fun toggleTheme(isDark: Boolean) = settingsManager.setDarkTheme(isDark)
}
