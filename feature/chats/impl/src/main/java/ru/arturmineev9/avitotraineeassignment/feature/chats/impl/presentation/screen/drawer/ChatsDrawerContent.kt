package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.R

@Composable
fun ChatsDrawerContent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onMenuItemClick: (DrawerItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        DrawerSearchBar(searchQuery, onSearchQueryChange)

        DrawerMenuItems(onMenuItemClick)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.chats_assistant_label),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
