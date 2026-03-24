package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.data.datasource

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class ProfileFileDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun saveAvatar(imageUri: Uri, userId: String): String {
        context.filesDir.listFiles { _, name -> name.startsWith("avatar_$userId") }
            ?.forEach { it.delete() }

        val fileName = "avatar_${userId}_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        val inputStream = context.contentResolver.openInputStream(imageUri)
            ?: throw IllegalStateException("Cannot open uri")

        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }
}
