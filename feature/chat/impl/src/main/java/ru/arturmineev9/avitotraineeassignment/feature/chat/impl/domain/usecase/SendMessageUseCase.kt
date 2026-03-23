package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(chatId: String, text: String): Result<Unit> {
        if (text.isBlank()) return Result.failure(Exception("Сообщение не может быть пустым"))
        return repository.sendMessage(chatId, text)
    }
}
