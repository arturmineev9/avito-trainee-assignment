package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatEvent
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatState
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components.ChatInputBar
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components.ChatMessagesList
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components.ChatTopAppBar
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components.RenameChatDialog

@Composable
fun ChatScreen(
    state: ChatState,
    listState: LazyListState,
    snackBarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onEvent: (ChatEvent) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            ChatTopAppBar(
                title = state.chatTitle,
                onBackClick = onBackClick,
                onRenameClick = { onEvent(ChatEvent.RenameMenuClicked) }
            )
        },
        bottomBar = {
            ChatInputBar(
                text = state.inputText,
                onTextChange = { onEvent(ChatEvent.InputTextChanged(it)) },
                onSendClick = { onEvent(ChatEvent.SendMessageClicked) },
                isEnabled = !state.isAiTyping
            )
        }
    ) { padding ->
        ChatMessagesList(
            state = state,
            listState = listState,
            paddingValues = padding,
            onEvent = onEvent
        )
    }

    if (state.isRenameDialogVisible) {
        RenameChatDialog(
            renameInput = state.renameInput,
            onEvent = onEvent
        )
    }
}
