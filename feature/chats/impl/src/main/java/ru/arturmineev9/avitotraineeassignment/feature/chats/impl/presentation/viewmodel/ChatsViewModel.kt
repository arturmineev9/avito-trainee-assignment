package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import ru.arturmineev9.avitotraineeassignment.core.ui.mvi.BaseViewModel
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.domain.model.Chat
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsEffect
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsEvent
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsState
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.domain.CreateNewChatUseCase
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.domain.GetChatsUseCase
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val createNewChatUseCase: CreateNewChatUseCase
) : BaseViewModel<ChatsState, ChatsEvent, ChatsEffect>(ChatsState()) {

    private val searchQueryFlow = MutableStateFlow("")

    val pagedChats: Flow<PagingData<Chat>> = searchQueryFlow
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getChatsUseCase(query)
        }
        .cachedIn(viewModelScope)

    override fun onEvent(event: ChatsEvent) {
        when (event) {
            is ChatsEvent.SearchIconClicked -> setState { copy(isSearchActive = true) }

            is ChatsEvent.CloseSearchClicked -> {
                setState { copy(isSearchActive = false, searchQuery = "") }
                searchQueryFlow.value = ""
            }

            is ChatsEvent.SearchQueryChanged -> {
                setState { copy(searchQuery = event.query) }
                searchQueryFlow.value = event.query
            }

            is ChatsEvent.CreateNewChatClicked -> createNewChat()
            is ChatsEvent.OpenDrawerClicked -> setEffect { ChatsEffect.OpenDrawer }
            is ChatsEvent.ProfileMenuClicked -> setEffect { ChatsEffect.NavigateToProfile }
        }
    }

    private fun createNewChat() {
        if (currentState.isCreatingChat) return

        viewModelScope.launch {
            setState { copy(isCreatingChat = true) }

            val result = createNewChatUseCase(title = "Новый чат")

            result.onSuccess { newChatId ->
                setEffect { ChatsEffect.NavigateToChatDetail(newChatId) }
            }.onFailure { error ->
                setEffect { ChatsEffect.ShowError(error) }
            }

            setState { copy(isCreatingChat = false) }
        }
    }
}
