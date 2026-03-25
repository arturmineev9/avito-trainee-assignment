package ru.arturmineev9.avitotraineeassignment.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.arturmineev9.avitotraineeassignment.core.network.api.TokenProvider
import ru.arturmineev9.avitotraineeassignment.core.network.auth.TokenManager

@Module
@InstallIn(SingletonComponent::class) // Привязываем к жизненному циклу всего приложения
abstract class AuthModule {
    @Binds
    abstract fun bindTokenProvider(
        tokenManager: TokenManager
    ): TokenProvider
}
