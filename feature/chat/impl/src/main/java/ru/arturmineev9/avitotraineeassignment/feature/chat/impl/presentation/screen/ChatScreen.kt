package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
            var isMenuExpanded by remember { mutableStateOf(false) }
            TopAppBar(
                title = {
                    Text(
                        text = state.chatTitle,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Меню чата")
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Изменить название") },
                            onClick = {
                                isMenuExpanded = false
                                onEvent(ChatEvent.RenameMenuClicked)
                            },
                            leadingIcon = { Icon(Icons.Default.Edit, null) }
                        )
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
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

    if (state.isRenameDialogVisible) {
        AlertDialog(
            onDismissRequest = { onEvent(ChatEvent.RenameDialogDismissed) },
            title = { Text("Изменить название") },
            text = {
                OutlinedTextField(
                    value = state.renameInput,
                    onValueChange = { onEvent(ChatEvent.RenameInputChanged(it)) },
                    label = { Text("Название чата") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { onEvent(ChatEvent.SaveNewTitleClicked) }
                ) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onEvent(ChatEvent.RenameDialogDismissed) }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}
