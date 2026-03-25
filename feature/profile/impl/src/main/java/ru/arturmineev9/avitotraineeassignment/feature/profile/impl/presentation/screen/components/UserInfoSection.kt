package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileEvent
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.presentation.ProfileState
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.R

@Composable
fun UserInfoSection(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    val emptyValue = stringResource(R.string.profile_empty_value)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Column(Modifier.padding(16.dp)) {
            ProfileDataRow(
                label = stringResource(R.string.profile_email_label),
                value = state.profile?.email ?: emptyValue
            )

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
                    label = stringResource(R.string.profile_name_label),
                    value = state.profile?.name ?: emptyValue,
                    isEditable = true,
                    onEditClick = { onEvent(ProfileEvent.EditNameClicked) }
                )
            }

            HorizontalDivider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

            ProfileDataRow(
                label = stringResource(R.string.profile_balance_label),
                value = stringResource(R.string.profile_tokens_format, state.profile?.tokensCount ?: 0)
            )
        }
    }
}
