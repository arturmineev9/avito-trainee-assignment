package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.arturmineev9.avitotraineeassignment.feature.profile.impl.R

@Composable
fun LogoutButton(onLogout: () -> Unit) {
    Button(
        onClick = onLogout,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(Icons.Default.ExitToApp, null)
        Spacer(Modifier.width(8.dp))
        Text(stringResource(R.string.profile_logout_button), fontWeight = FontWeight.Bold)
    }
}
