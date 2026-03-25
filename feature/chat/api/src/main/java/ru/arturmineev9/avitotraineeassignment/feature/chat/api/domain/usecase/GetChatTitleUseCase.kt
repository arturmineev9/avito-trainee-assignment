package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetChatTitleUseCase {
    operator fun invoke(chatId: String): Flow<String>
}
