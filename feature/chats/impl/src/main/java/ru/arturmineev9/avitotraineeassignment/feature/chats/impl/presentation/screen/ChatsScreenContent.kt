package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsEvent
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsState
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.components.ChatItem
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.components.SearchBarTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreenContent(
    state: ChatsState,
    pagedChats: LazyPagingItems<Chat>,
    snackBarHostState: SnackbarHostState,
    onEvent: (ChatsEvent) -> Unit,
    onChatClick: (String) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            if (state.isSearchActive) {
                SearchBarTopBar(
                    query = state.searchQuery,
                    onQueryChange = { onEvent(ChatsEvent.SearchQueryChanged(it)) },
                    onCloseClick = { onEvent(ChatsEvent.CloseSearchClicked) }
                )
            } else {
                TopAppBar(
                    title = { Text("Чаты", fontWeight = FontWeight.SemiBold) },
                    navigationIcon = {
                        IconButton(onClick = { onEvent(ChatsEvent.OpenDrawerClicked) }) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    },
                    actions = {
                        IconButton(onClick = { onEvent(ChatsEvent.SearchIconClicked) }) {
                            Icon(Icons.Default.Search, contentDescription = "Поиск")
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ChatsEvent.CreateNewChatClicked) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                if (state.isCreatingChat) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(Icons.Default.Add, contentDescription = "Новый чат")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(
                count = pagedChats.itemCount,
                key = pagedChats.itemKey { it.id }
            ) { index ->
                val chat = pagedChats[index]
                if (chat != null) {
                    ChatItem(chat = chat, onClick = { onChatClick(chat.id) })
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }

            pagedChats.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.size(32.dp))
                            }
                        }
                    }
                    loadState.refresh is LoadState.NotLoading && pagedChats.itemCount == 0 -> {
                        item {
                            Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Список чатов пуст", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}
