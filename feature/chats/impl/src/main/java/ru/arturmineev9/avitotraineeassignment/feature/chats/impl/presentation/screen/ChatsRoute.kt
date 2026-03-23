package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ru.arturmineev9.avitotraineeassignment.core.navigation.navigator.ChatsNavigator
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsEffect
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsEvent
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.mapper.toUiText
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.viewmodel.ChatsViewModel

@Composable
fun ChatsRoute(
    navigator: ChatsNavigator,
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val pagedChats = viewModel.pagedChats.collectAsLazyPagingItems()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ChatsEffect.NavigateToChatDetail -> navigator.navigateToChatDetail(effect.chatId)
                is ChatsEffect.NavigateToProfile -> navigator.navigateToProfile()
                is ChatsEffect.ShowError -> {
                    val errorMessage = effect.error.toUiText(context)
                    snackBarHostState.showSnackbar(
                        message = errorMessage,
                        duration = SnackbarDuration.Short
                    )
                }
                is ChatsEffect.OpenDrawer -> {
                    scope.launch { drawerState.open() }
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ChatsDrawerContent(
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = { viewModel.onEvent(ChatsEvent.SearchQueryChanged(it)) },
                    onNewChatClick = {
                        scope.launch { drawerState.close() }
                        viewModel.onEvent(ChatsEvent.CreateNewChatClicked)
                    },
                    onHomeClick = {
                        scope.launch { drawerState.close() }
                    },
                    onImagesClick = {
                        scope.launch { drawerState.close() }
                        // TODO
                    },
                    onProfileClick = {
                        scope.launch { drawerState.close() }
                        viewModel.onEvent(ChatsEvent.ProfileMenuClicked)
                    }
                )
            }
        }
    ) {
        ChatsScreenContent(
            state = state,
            pagedChats = pagedChats,
            snackBarHostState = snackBarHostState,
            onEvent = viewModel::onEvent,
            onChatClick = { chatId -> navigator.navigateToChatDetail(chatId) }
        )
    }
}
