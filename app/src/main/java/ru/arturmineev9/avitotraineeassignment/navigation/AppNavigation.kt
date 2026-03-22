package ru.arturmineev9.avitotraineeassignment.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.AuthRoute


package ru.arturmineev9.avitotraineeassignment.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.AuthRoute

@Composable
fun AppNavigation(startDestination: AppGraph = AppGraph.AuthGraph) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Явно указываем типы параметров лямбды
        composable<AppGraph.AuthGraph> { _, _ ->
            AuthRoute(
                navigateToChats = {
                    navController.navigate(AppGraph.ChatsGraph) {
                        popUpTo<AppGraph.AuthGraph> { inclusive = true }
                    }
                }
            )
        }

        composable<AppGraph.ChatsGraph> { _, _ ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Добро пожаловать в список чатов! (Завтра сделаем)")
            }
        }
    }
}
