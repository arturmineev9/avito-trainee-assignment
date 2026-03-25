package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatEvent
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatState

@Composable
fun ChatMessagesList(
    state: ChatState,
    listState: LazyListState,
    paddingValues: PaddingValues,
    onEvent: (ChatEvent) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
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
