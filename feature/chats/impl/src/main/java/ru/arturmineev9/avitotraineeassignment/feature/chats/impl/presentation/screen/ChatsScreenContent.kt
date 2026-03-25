package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsEvent
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsState
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.R
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.components.ChatsListContent
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
                    title = { Text(stringResource(R.string.chats_title), fontWeight = FontWeight.SemiBold) },
                    navigationIcon = {
                        IconButton(onClick = { onEvent(ChatsEvent.OpenDrawerClicked) }) {
                            Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.cd_menu))
                        }
                    },
                    actions = {
                        IconButton(onClick = { onEvent(ChatsEvent.SearchIconClicked) }) {
                            Icon(Icons.Default.Search, contentDescription = stringResource(R.string.cd_search))
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
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.cd_new_chat))
                }
            }
        }
    ) { paddingValues ->
        ChatsListContent(
            pagedChats = pagedChats,
            paddingValues = paddingValues,
            onChatClick = onChatClick
        )
    }
}
