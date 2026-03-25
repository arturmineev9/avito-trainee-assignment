package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onCloseClick: () -> Unit
) {
    TopAppBar(
        title = {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text(stringResource(R.string.chats_search_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true
            )
        },
        navigationIcon = {
            IconButton(onClick = onCloseClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.cd_back))
            }
        },
        actions = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.cd_clear))
                }
            }
        }
    )
}
