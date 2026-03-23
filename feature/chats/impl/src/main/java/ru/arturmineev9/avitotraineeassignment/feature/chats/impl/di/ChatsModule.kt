package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.repository.ChatsRepository
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.data.repository.ChatsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatsBindsModule {

    @Binds
    @Singleton
    abstract fun bindChatsRepository(
        impl: ChatsRepositoryImpl
    ): ChatsRepository
}
