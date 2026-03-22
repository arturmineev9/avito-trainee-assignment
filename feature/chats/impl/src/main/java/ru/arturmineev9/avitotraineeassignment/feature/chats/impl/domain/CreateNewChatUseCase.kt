package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.domain

import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.repository.ChatsRepository
import javax.inject.Inject

class CreateNewChatUseCase @Inject constructor(
    private val repository: ChatsRepository
) {
    suspend operator fun invoke(title: String = "Новый чат"): Result<String> {
        return repository.createNewChat(title)
    }
}
