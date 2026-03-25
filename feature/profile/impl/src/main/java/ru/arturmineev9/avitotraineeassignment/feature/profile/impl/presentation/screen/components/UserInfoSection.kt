package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileEvent
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileState

@Composable
fun UserInfoSection(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Column(Modifier.padding(16.dp)) {
            ProfileDataRow(label = "E-mail", value = state.profile?.email ?: "—")

            HorizontalDivider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

            if (state.isEditingName) {
                NameEditField(
                    value = state.editNameInput,
                    onValueChange = { onEvent(ProfileEvent.NameInputChanged(it)) },
                    onSave = { onEvent(ProfileEvent.SaveNameClicked) },
                    isLoading = state.isUpdatingName
                )
            } else {
                ProfileDataRow(
                    label = "Имя",
                    value = state.profile?.name ?: "—",
                    isEditable = true,
                    onEditClick = { onEvent(ProfileEvent.EditNameClicked) }
                )
            }

            HorizontalDivider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

            ProfileDataRow(label = "Баланс", value = "${state.profile?.tokensCount} токенов")
        }
    }
}
