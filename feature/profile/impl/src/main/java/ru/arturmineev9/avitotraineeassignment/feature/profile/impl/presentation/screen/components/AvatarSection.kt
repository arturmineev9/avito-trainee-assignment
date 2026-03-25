package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AvatarSection(
    photoUrl: String?,
    isUploading: Boolean,
    onPickPhoto: () -> Unit
) {
    val personPainter = rememberVectorPainter(image = Icons.Default.Person)

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(enabled = !isUploading) { onPickPhoto() },
        contentAlignment = Alignment.Center
    ) {
        if (photoUrl.isNullOrEmpty()) {
            Icon(
                painter = personPainter,
                contentDescription = "Avatar placeholder",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            AsyncImage(
                model = photoUrl,
                contentDescription = "User Avatar",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = personPainter,
                placeholder = personPainter
            )
        }

        if (isUploading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
