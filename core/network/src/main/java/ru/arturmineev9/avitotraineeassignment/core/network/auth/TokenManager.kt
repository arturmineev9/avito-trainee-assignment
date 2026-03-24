package ru.arturmineev9.avitotraineeassignment.core.network.auth

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.arturmineev9.avitotraineeassignment.core.network.BuildConfig
import ru.arturmineev9.avitotraineeassignment.core.network.api.GigaChatAuthApi
import ru.arturmineev9.avitotraineeassignment.core.network.api.TokenProvider
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val authApi: GigaChatAuthApi
) : TokenProvider {
    private val mutex = Mutex()
    private var cachedToken: String? = null

    override suspend fun getAccessToken(): String? = cachedToken

    suspend fun refreshToken(): String? = mutex.withLock {
        return try {
            val rqUid = UUID.randomUUID().toString()
            val response = authApi.getAccessToken(
                authorization = "Basic ${BuildConfig.GIGACHAT_AUTH_KEY}",
                rqUid = rqUid
            )
            cachedToken = response.accessToken
            cachedToken
        } catch (e: Exception) {
            null
        }
    }
}
