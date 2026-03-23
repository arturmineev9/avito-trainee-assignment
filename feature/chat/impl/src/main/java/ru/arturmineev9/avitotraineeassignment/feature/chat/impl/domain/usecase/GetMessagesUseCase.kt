package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.model.Message
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(chatId: String): Flow<List<Message>> = repository.getMessages(chatId)
}
