package ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.usecase

interface CreateNewChatUseCase {
    suspend operator fun invoke(title: String = "Новый чат"): Result<String>
}
