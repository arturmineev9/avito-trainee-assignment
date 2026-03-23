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
import androidx.navigation.toRoute
import ru.arturmineev9.avitotraineeassignment.core.navigation.AppGraph
import ru.arturmineev9.avitotraineeassignment.core.navigation.navigator.AuthNavigator
import ru.arturmineev9.avitotraineeassignment.core.navigation.navigator.ChatsNavigator
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.screen.AuthRoute
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.ChatsRoute

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
            val chatsNavigator = remember {
                object : ChatsNavigator {
                    override fun navigateToChatDetail(chatId: String) {
                        navController.navigate(AppGraph.ChatDetailGraph(chatId))
                    }

                    override fun navigateToProfile() {
                        navController.navigate(AppGraph.ProfileGraph)
                    }
                }
            }
            ChatsRoute(navigator = chatsNavigator)
        }

        composable<AppGraph.ChatDetailGraph> { backStackEntry ->
            val args = backStackEntry.toRoute<AppGraph.ChatDetailGraph>()
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Чат: ${args.chatId}")
            }
        }

        composable<AppGraph.ProfileGraph> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Профиль пользователя")
            }
        }
    }
}
