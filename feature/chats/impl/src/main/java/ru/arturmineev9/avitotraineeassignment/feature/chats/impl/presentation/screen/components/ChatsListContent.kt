package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.R

@Composable
fun ChatsListContent(
    pagedChats: LazyPagingItems<Chat>,
    paddingValues: PaddingValues,
    onChatClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        items(count = pagedChats.itemCount, key = pagedChats.itemKey { it.id }) { index ->
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
                        Box(
                            Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator() }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator(modifier = Modifier.size(32.dp)) }
                    }
                }

                loadState.refresh is LoadState.NotLoading && pagedChats.itemCount == 0 -> {
                    item {
                        Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(R.string.chats_empty_list),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
