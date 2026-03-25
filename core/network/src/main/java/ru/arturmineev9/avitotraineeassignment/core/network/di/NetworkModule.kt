package ru.arturmineev9.avitotraineeassignment.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.arturmineev9.avitotraineeassignment.core.network.BuildConfig
import ru.arturmineev9.avitotraineeassignment.core.network.api.GigaChatApi
import ru.arturmineev9.avitotraineeassignment.core.network.api.GigaChatAuthApi
import ru.arturmineev9.avitotraineeassignment.core.network.interceptor.AuthInterceptor
import ru.arturmineev9.avitotraineeassignment.core.network.interceptor.GigaChatAuthenticator
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthNetwork

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiNetwork

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @AuthNetwork
    fun provideAuthOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGigaChatAuthApi(
        @AuthNetwork okHttpClient: OkHttpClient,
        json: Json
    ): GigaChatAuthApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GIGACHAT_AUTH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GigaChatAuthApi::class.java)
    }

    @Provides
    @Singleton
    @ApiNetwork
    fun provideApiOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        authenticator: GigaChatAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGigaChatApi(
        @ApiNetwork okHttpClient: OkHttpClient,
        json: Json
    ): GigaChatApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GIGACHAT_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GigaChatApi::class.java)
    }
}
