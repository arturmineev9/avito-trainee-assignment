package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ChatsDrawerContent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNewChatClick: () -> Unit,
    onHomeClick: () -> Unit,
    onImagesClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Поиск по чатам") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = if (searchQuery.isNotEmpty()) {
                {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            } else null,
            shape = CircleShape,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            )
        )

        NavigationDrawerItem(
            label = { Text("Новый чат") },
            selected = false,
            onClick = onNewChatClick,
            icon = { Icon(Icons.Default.Add, contentDescription = null) },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        NavigationDrawerItem(
            label = { Text("Изображения") },
            selected = false,
            onClick = onImagesClick,
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        NavigationDrawerItem(
            label = { Text("Список чатов") },
            selected = true,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        NavigationDrawerItem(
            label = { Text("Профиль") },
            selected = false,
            onClick = onProfileClick,
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            "GigaChat AI Assistant",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
