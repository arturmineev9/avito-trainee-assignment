package ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat

interface GetChatsUseCase {
    operator fun invoke(searchQuery: String): Flow<PagingData<Chat>>
}