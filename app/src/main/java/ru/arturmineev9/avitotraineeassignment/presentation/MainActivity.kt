package ru.arturmineev9.avitotraineeassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.arturmineev9.avitotraineeassignment.navigation.AppNavigation
import ru.arturmineev9.avitotraineeassignment.ui.theme.AvitoTraineeAssignmentTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        setContent {
            AvitoTraineeAssignmentTheme {
                val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
                val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

                if (isLoading) {
                    //SplashScreen()
                } else {
                    AppNavigation(
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
