package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatEvent
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.R

@Composable
fun RenameChatDialog(
    renameInput: String,
    onEvent: (ChatEvent) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onEvent(ChatEvent.RenameDialogDismissed) },
        title = { Text(stringResource(R.string.dialog_rename_title)) },
        text = {
            OutlinedTextField(
                value = renameInput,
                onValueChange = { onEvent(ChatEvent.RenameInputChanged(it)) },
                label = { Text(stringResource(R.string.dialog_rename_label)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onEvent(ChatEvent.SaveNewTitleClicked) }
            ) {
                Text(stringResource(R.string.dialog_save))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onEvent(ChatEvent.RenameDialogDismissed) }
            ) {
                Text(stringResource(R.string.dialog_cancel))
            }
        }
    )
}
