package ru.arturmineev9.avitotraineeassignment.core.navigation

import kotlinx.serialization.Serializable

sealed interface AppGraph {
    @Serializable
    data object AuthGraph : AppGraph
    @Serializable
    data object ChatsGraph : AppGraph
}
