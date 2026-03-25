package ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation

import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiEffect
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiEvent
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.UiState
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model.Message

data class ChatState(
    val chatId: String = "",
    val chatTitle: String = "Загрузка...",
    val isRenameDialogVisible: Boolean = false,
    val renameInput: String = "",
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val isAiTyping: Boolean = false,
    val error: Throwable? = null
) : UiState

sealed interface ChatEvent : UiEvent {
    data class LoadMessages(val chatId: String) : ChatEvent
    data class InputTextChanged(val text: String) : ChatEvent
    object SendMessageClicked : ChatEvent
    data class ShareMessageClicked(val text: String) : ChatEvent
    object RenameMenuClicked : ChatEvent
    object RenameDialogDismissed : ChatEvent
    data class RenameInputChanged(val text: String) : ChatEvent
    object SaveNewTitleClicked : ChatEvent
}

sealed interface ChatEffect : UiEffect {
    data class ShowError(val error: Throwable) : ChatEffect
    data class ShareText(val text: String) : ChatEffect
    object ScrollToBottom : ChatEffect
}
