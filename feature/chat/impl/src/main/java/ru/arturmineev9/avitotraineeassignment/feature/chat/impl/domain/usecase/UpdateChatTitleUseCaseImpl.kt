package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.exception.ChatException
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository.ChatRepository
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase.UpdateChatTitleUseCase
import javax.inject.Inject

class UpdateChatTitleUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : UpdateChatTitleUseCase {
    override suspend operator fun invoke(chatId: String, newTitle: String): Result<Unit> {
        val trimmedTitle = newTitle.trim()

        if (trimmedTitle.isEmpty()) {
            return Result.failure(ChatException.Unknown("Название чата не может быть пустым"))
        }

        return repository.updateChatTitle(chatId, trimmedTitle)
    }
}
