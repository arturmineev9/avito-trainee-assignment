package ru.arturmineev9.avitotraineeassignment.navigation

sealed class AppGraph {
    data object AuthGraph : AppGraph()
    data object ChatsGraph : AppGraph()
}
