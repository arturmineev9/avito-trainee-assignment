package ru.arturmineev9.avitotraineeassignment.core.navigation

import kotlinx.serialization.Serializable
@Serializable
sealed interface AppGraph {
    @Serializable
    data object AuthGraph : AppGraph
    @Serializable
    data object ChatsGraph : AppGraph
    @Serializable
    data class ChatDetailGraph(val chatId: String) : AppGraph

    @Serializable
    data object ProfileGraph : AppGraph
}
