package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.repository.ChatsRepository
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.usecase.GetChatsUseCase
import javax.inject.Inject

class GetChatsUseCaseImpl @Inject constructor(
    private val repository: ChatsRepository
) : GetChatsUseCase {
    override operator fun invoke(searchQuery: String): Flow<PagingData<Chat>> {
        return if (searchQuery.isBlank()) {
            repository.getChatsPaged()
        } else {
            repository.searchChatsPaged(searchQuery)
        }
    }
}
