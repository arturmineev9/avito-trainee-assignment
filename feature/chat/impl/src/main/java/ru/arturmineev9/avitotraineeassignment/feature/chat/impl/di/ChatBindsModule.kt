package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.data.datasource.LocalChatDataSource
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.data.datasource.RemoteChatDataSource
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository.ChatRepository
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase.GetChatTitleUseCase
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase.GetMessagesUseCase
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase.SendMessageUseCase
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase.UpdateChatTitleUseCase
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource.LocalChatDataSourceImpl
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.datasource.RemoteChatDataSourceImpl
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.repository.ChatRepositoryImpl
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase.GetChatTitleUseCaseImpl
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase.GetMessagesUseCaseImpl
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase.SendMessageUseCaseImpl
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase.UpdateChatTitleUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatBindsModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun bindGetChatTitleUseCase(
        impl: GetChatTitleUseCaseImpl
    ): GetChatTitleUseCase

    @Binds
    @Singleton
    abstract fun bindGetMessagesUseCase(
        impl: GetMessagesUseCaseImpl
    ): GetMessagesUseCase

    @Binds
    @Singleton
    abstract fun bindSendMessageUseCase(
        impl: SendMessageUseCaseImpl
    ): SendMessageUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateChatTitleUseCase(
        impl: UpdateChatTitleUseCaseImpl
    ): UpdateChatTitleUseCase

    @Binds
    abstract fun bindLocalChatDataSource(
        impl: LocalChatDataSourceImpl
    ): LocalChatDataSource

    @Binds
    abstract fun bindRemoteChatDataSource(
        impl: RemoteChatDataSourceImpl
    ): RemoteChatDataSource
}
