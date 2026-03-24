package ru.arturmineev9.avitotraineeassignment.core.network.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.arturmineev9.avitotraineeassignment.core.network.auth.TokenManager
import javax.inject.Inject

class GigaChatAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= 3) {
            return null
        }

        val newToken = runBlocking { tokenManager.refreshToken() }

        if (newToken == null) {
            return null
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }
}

private val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
