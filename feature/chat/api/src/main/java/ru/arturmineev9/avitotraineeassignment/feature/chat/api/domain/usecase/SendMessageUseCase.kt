package ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase

interface SendMessageUseCase {
    suspend operator fun invoke(chatId: String, text: String): Result<Unit>
}
