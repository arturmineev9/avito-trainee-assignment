package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileDataRow(
    label: String,
    value: String,
    isEditable: Boolean = false,
    onEditClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        }
        if (isEditable) {
            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, "Edit", Modifier.size(20.dp))
            }
        }
    }
}
