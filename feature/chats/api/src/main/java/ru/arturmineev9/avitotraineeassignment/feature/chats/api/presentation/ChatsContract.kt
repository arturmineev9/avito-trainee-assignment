package ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation

import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiEffect
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiEvent
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiState

data class ChatsState(
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val isCreatingChat: Boolean = false
) : UiState

sealed interface ChatsEvent : UiEvent {
    object SearchIconClicked : ChatsEvent
    object CloseSearchClicked : ChatsEvent
    data class SearchQueryChanged(val query: String) : ChatsEvent
    object CreateNewChatClicked : ChatsEvent
    object OpenDrawerClicked : ChatsEvent
    object ProfileMenuClicked : ChatsEvent
}

sealed interface ChatsEffect : UiEffect {
    data class NavigateToChatDetail(val chatId: String) : ChatsEffect
    object NavigateToProfile : ChatsEffect
    data class ShowError(val error: Throwable) : ChatsEffect
    object OpenDrawer : ChatsEffect
}
