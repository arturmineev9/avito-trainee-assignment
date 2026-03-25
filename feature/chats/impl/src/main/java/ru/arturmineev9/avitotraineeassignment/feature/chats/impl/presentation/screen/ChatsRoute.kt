package ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ru.arturmineev9.avitotraineeassignment.core.navigation.navigator.ChatsNavigator
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsEffect
import ru.arturmineev9.avitotraineeassignment.feature.chats.api.presentation.ChatsEvent
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.mapper.toUiText
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.drawer.ChatsDrawerContent
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.screen.drawer.DrawerItem
import ru.arturmineev9.avitotraineeassignment.feature.chats.impl.presentation.viewmodel.ChatsViewModel

private const val DRAWER_WIDTH_FRACTION = 0.75f
@Composable
fun ChatsRoute(
    navigator: ChatsNavigator,
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagedChats = viewModel.pagedChats.collectAsLazyPagingItems()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(DRAWER_WIDTH_FRACTION))
            {
                ChatsDrawerContent(
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = { viewModel.onEvent(ChatsEvent.SearchQueryChanged(it)) },
                    onMenuItemClick = { item ->
                        scope.launch { drawerState.close() }
                        when (item) {
                            DrawerItem.NEW_CHAT -> viewModel.onEvent(ChatsEvent.CreateNewChatClicked)
                            DrawerItem.CHAT_LIST -> {}
                            DrawerItem.IMAGES -> { /* TODO */ }
                            DrawerItem.PROFILE -> viewModel.onEvent(ChatsEvent.ProfileMenuClicked)
                        }
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
