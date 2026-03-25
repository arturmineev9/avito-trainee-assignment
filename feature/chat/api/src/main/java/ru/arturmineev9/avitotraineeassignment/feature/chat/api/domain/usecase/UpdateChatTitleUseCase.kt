package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase

interface UpdateChatTitleUseCase {
    suspend operator fun invoke(chatId: String, newTitle: String): Result<Unit>
}
