package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.BaseViewModel
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.exception.ChatException
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatEffect
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatEvent
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatState
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase.GetMessagesUseCase
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase.SendMessageUseCase
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : BaseViewModel<ChatState, ChatEvent, ChatEffect>(ChatState()) {

    override fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.LoadMessages -> loadMessages(event.chatId)
            is ChatEvent.InputTextChanged -> setState { copy(inputText = event.text) }
            is ChatEvent.SendMessageClicked -> sendMessage()
            is ChatEvent.ShareMessageClicked -> setEffect { ChatEffect.ShareText(event.text) }
        }
    }

    private fun loadMessages(chatId: String) {
        setState { copy(chatId = chatId) }
        viewModelScope.launch {
            getMessagesUseCase(chatId).collectLatest { list ->
                setState { copy(messages = list) }
                setEffect { ChatEffect.ScrollToBottom }
            }
        }
    }

    private fun sendMessage() {
        val text = currentState.inputText.trim()
        val chatId = currentState.chatId
        if (text.isEmpty() || currentState.isAiTyping) return

        viewModelScope.launch {
            setState { copy(inputText = "", isAiTyping = true) }

            val result = sendMessageUseCase(chatId, text)

            result.onSuccess {
                setEffect { ChatEffect.ScrollToBottom }
            }.onFailure { error ->
                val chatError = when {
                    error is java.net.UnknownHostException -> ChatException.NetworkError()
                    error.message?.contains("401") == true -> ChatException.AuthError()
                    error.message?.contains("429") == true -> ChatException.DailyLimitReached()
                    else -> ChatException.Unknown(error.message)
                }
                setEffect { ChatEffect.ShowError(chatError) }
            }

            setState { copy(isAiTyping = false) }
        }
    }
}
