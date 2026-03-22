package ru.arturmineev9.avitotraineeassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.arturmineev9.avitotraineeassignment.ui.theme.AvitoTraineeAssignmentTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        setContent {
            AvitoTraineeAssignmentTheme {
                // Подписываемся на стейт загрузки
                val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
                val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

                // Пока идет проверка (доли секунды), можно показать пустой экран или логотип
                if (isLoading) {
                    SplashScreen() // Твой кастомный Splash
                } else {
                    // Когда проверка прошла, строим граф навигации с нужной стартовой точкой
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        startDestination = startDestination // Либо логин, либо чаты
                    )
                }
            }
        }
    }
}

