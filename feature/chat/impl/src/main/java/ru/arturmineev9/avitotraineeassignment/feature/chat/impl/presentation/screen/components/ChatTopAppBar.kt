package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    onRenameClick: () -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = title,
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
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.chat_menu_content_desc)
                )
            }
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.chat_menu_rename)) },
                    onClick = {
                        isMenuExpanded = false
                        onRenameClick()
                    },
                    leadingIcon = { Icon(Icons.Default.Edit, null) }
                )
            }
        }
    )
}
