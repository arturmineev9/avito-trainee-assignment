package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.R

@Composable
fun DrawerMenuItems(onClick: (DrawerItem) -> Unit) {
    NavigationDrawerItem(
        label = { Text(stringResource(R.string.drawer_new_chat)) },
        selected = false,
        onClick = { onClick(DrawerItem.NEW_CHAT) },
        icon = { Icon(Icons.Default.AddComment, contentDescription = null) },
        modifier = Modifier.padding(vertical = 4.dp)
    )
    NavigationDrawerItem(
        label = { Text(stringResource(R.string.drawer_images)) },
        selected = false,
        onClick = { onClick(DrawerItem.IMAGES) },
        icon = { Icon(Icons.Default.Image, contentDescription = null) },
        modifier = Modifier.padding(vertical = 4.dp)
    )
    NavigationDrawerItem(
        label = { Text(stringResource(R.string.drawer_chat_list)) },
        selected = true,
        onClick = { onClick(DrawerItem.CHAT_LIST) },
        icon = { Icon(Icons.Default.Chat, contentDescription = null) },
        modifier = Modifier.padding(vertical = 4.dp)
    )
    NavigationDrawerItem(
        label = { Text(stringResource(R.string.drawer_profile)) },
        selected = false,
        onClick = { onClick(DrawerItem.PROFILE) },
        icon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
