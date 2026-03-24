package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository.ChatRepository
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.repository.ChatRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatBindsModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: ChatRepositoryImpl
    ): ChatRepository
}
