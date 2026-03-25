package ru.arturmineev9.avitotraineeassignment.feature.profile.impl.data.datasource

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.arturmineev9.avitotraineeassignment.feature.profile.api.data.datastore.ProfileFileDataSource
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

// В связи с тем, что Firebase ввёл платежные ограничения на Firebase Storage,
// загрузить аватарку на облако возможности нет

class ProfileFileDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ProfileFileDataSource {

    override fun saveAvatar(imageUri: Uri, userId: String): String {
        // Удаляем старые аватарки пользователя
        context.filesDir.listFiles { _, name -> name.startsWith("avatar_$userId") }
            ?.forEach { it.delete() }

        val fileName = "avatar_${userId}_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        val inputStream = context.contentResolver.openInputStream(imageUri)
        checkNotNull(inputStream) { "Cannot open uri" }

        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }
}
