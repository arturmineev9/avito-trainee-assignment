package ru.arturmineev9.avitotraineeassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ru.arturmineev9.avitotraineeassignment.core.common.settings.SettingsManager
import ru.arturmineev9.avitotraineeassignment.navigation.AppNavigation
import ru.arturmineev9.avitotraineeassignment.ui.theme.AvitoTraineeAssignmentTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme by settingsManager.isDarkTheme.collectAsState(initial = false)

            AvitoTraineeAssignmentTheme(darkTheme = isDarkTheme) {
                val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
                val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

                if (!isLoading) {
                    AppNavigation(startDestination = startDestination)
                }
            }
        }
    }
}
