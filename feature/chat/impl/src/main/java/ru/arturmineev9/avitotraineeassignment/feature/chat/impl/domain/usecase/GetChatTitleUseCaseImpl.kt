package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.repository.ChatRepository
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.domain.usecase.GetChatTitleUseCase
import javax.inject.Inject

class GetChatTitleUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetChatTitleUseCase {
    override operator fun invoke(chatId: String): Flow<String> {
        return repository.getChatTitle(chatId)
    }
}
