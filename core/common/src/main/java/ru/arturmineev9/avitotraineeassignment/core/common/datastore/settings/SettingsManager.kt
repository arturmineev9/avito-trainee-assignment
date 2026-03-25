package ru.arturmineev9.avitotraineeassignment.core.common.datastore.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val themeKey = booleanPreferencesKey("is_dark_theme")

    private fun avatarKey(uid: String) = stringPreferencesKey("avatar_path_$uid")

    val isDarkTheme: Flow<Boolean> = dataStore.data.map { it[themeKey] ?: false }

    suspend fun setDarkTheme(isDark: Boolean) {
        dataStore.edit { it[themeKey] = isDark }
    }

    fun getAvatarPath(uid: String): Flow<String?> = dataStore.data.map { it[avatarKey(uid)] }

    suspend fun saveAvatarPath(uid: String, path: String) {
        dataStore.edit { it[avatarKey(uid)] = path }
    }
}
