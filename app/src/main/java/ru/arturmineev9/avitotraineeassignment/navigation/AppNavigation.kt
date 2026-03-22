package ru.arturmineev9.avitotraineeassignment.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.arturmineev9.avitotraineeassignment.core.navigation.AppGraph
import ru.arturmineev9.avitotraineeassignment.core.navigation.navigator.AuthNavigator
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.AuthRoute

@Composable
fun AppNavigation(startDestination: AppGraph = AppGraph.AuthGraph) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<AppGraph.AuthGraph> {
            val authNavigator = remember {
                object : AuthNavigator {
                    override fun navigateToChats() {
                        navController.navigate(AppGraph.ChatsGraph) {
                            popUpTo<AppGraph.AuthGraph> { inclusive = true }
                        }
                    }
                }
            }

            AuthRoute(navigator = authNavigator)
        }

        composable<AppGraph.ChatsGraph> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Добро пожаловать в список чатов! (Завтра сделаем)")
            }
        }
    }
}
