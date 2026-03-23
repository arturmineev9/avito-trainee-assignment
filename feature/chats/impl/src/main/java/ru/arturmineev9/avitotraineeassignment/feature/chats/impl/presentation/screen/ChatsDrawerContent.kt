package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun ChatsDrawerContent(
    onProfileClick: () -> Unit,
    onNewChatClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Text(
            "AI Assistant",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        NavigationDrawerItem(
            label = { Text("Главная / Список чатов") },
            selected = true,
            onClick = {  },
            icon = { Icon(Icons.Default.Person, contentDescription = null) }
        )

        NavigationDrawerItem(
            label = { Text("Новый чат") },
            selected = false,
            onClick = onNewChatClick,
            icon = { Icon(Icons.Default.Person, contentDescription = null) }
        )

        NavigationDrawerItem(
            label = { Text("Профиль") },
            selected = false,
            onClick = onProfileClick,
            icon = { Icon(Icons.Default.Person, contentDescription = null) }
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            "Avito Trainee Spring 2026",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
