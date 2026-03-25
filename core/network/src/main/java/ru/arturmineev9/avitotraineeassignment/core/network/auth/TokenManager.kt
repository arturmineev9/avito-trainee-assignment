package ru.arturmineev9.avitotraineeassignment.core.network.auth

import android.util.Log
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException
import ru.arturmineev9.avitotraineeassignment.core.network.BuildConfig
import ru.arturmineev9.avitotraineeassignment.core.network.api.GigaChatAuthApi
import ru.arturmineev9.avitotraineeassignment.core.network.api.TokenProvider
import java.io.IOException
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
        } catch (e: IOException) {
            Log.e("TokenManager", "Network error while refreshing token", e)
            null
        } catch (e: HttpException) {
            Log.e("TokenManager", "HTTP error while refreshing token", e)
            null
        }
    }
}
