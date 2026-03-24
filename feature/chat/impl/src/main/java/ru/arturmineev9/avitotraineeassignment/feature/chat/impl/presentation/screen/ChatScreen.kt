package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatEvent
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatState
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components.ChatInputBar
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components.MessageBubble
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components.TypingIndicator

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(
                title = { Text("GigaChat", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
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
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            reverseLayout = true
        ) {
            if (state.isAiTyping) {
                item { TypingIndicator() }
            }
            items(state.messages.asReversed(), key = { it.id }) { message ->
                MessageBubble(
                    message = message,
                    onLongClick = { onEvent(ChatEvent.ShareMessageClicked(message.text)) }
                )
            }
        }
    }
}
