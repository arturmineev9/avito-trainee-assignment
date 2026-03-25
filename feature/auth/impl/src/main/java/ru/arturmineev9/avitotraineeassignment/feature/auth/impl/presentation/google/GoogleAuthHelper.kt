package ru.arturmineev9.avitotraineeassignment.feature.auth.impl.presentation.google

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.exception.AuthException
import ru.arturmineev9.avitotraineeassignment.feature.auth.impl.R

suspend fun launchGoogleSignIn(context: Context): Result<String> {
    return try {
        val credentialManager = CredentialManager.create(context)
        val webClientId = context.getString(R.string.default_web_client_id)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credentialManager.getCredential(context, request)
        val credential = result.credential

        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            Result.success(googleIdTokenCredential.idToken)
        } else {
            Result.failure(AuthException.Unknown("Неподдерживаемый тип авторизации"))
        }
    } catch (e: GetCredentialException) {
        Result.failure(e)
    }
}
