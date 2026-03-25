package ru.arturmineev9.avitotraineeassignment.feature.profile.api.data.datastore

import android.net.Uri

interface ProfileFileDataSource {
    fun saveAvatar(imageUri: Uri, userId: String): String
}
