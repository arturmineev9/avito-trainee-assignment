package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model.Message

interface GetMessagesUseCase {
    operator fun invoke(chatId: String): Flow<List<Message>>
}
