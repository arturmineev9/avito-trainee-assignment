package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.domain.usecase

import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.repository.ChatsRepository
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.usecase.CreateNewChatUseCase
import javax.inject.Inject

class CreateNewChatUseCaseImpl @Inject constructor(
    private val repository: ChatsRepository
) : CreateNewChatUseCase {
    override suspend operator fun invoke(title: String): Result<String> {
        return repository.createNewChat(title)
    }
}
