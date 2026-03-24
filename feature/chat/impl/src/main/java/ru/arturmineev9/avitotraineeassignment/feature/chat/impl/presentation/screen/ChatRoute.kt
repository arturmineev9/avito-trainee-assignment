package ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.screen

import android.content.Intent
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatEffect
import ru.arturmineev9.avitotraineeassignment.feature.chat.api.presentation.ChatEvent
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.data.mapper.toUiText
import ru.arturmineev9.avitotraineeassignment.feature.chat.impl.presentation.viewmodel.ChatViewModel

@Composable
fun ChatRoute(
    chatId: String,
    onBackClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(chatId) {
        viewModel.onEvent(ChatEvent.LoadMessages(chatId))
    }

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is ChatEffect.ScrollToBottom -> {
                        if (state.messages.isNotEmpty()) {
                            listState.animateScrollToItem(0)
                        }
                    }

                    is ChatEffect.ShowError -> {
                        val message = effect.error.toUiText(context)
                        snackBarHostState.showSnackbar(message)
                    }

                    is ChatEffect.ShareText -> {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, effect.text)
                        }
                        context.startActivity(Intent.createChooser(intent, "Поделиться"))
                    }
                }
            }
        }
    }

    ChatScreen(
        state = state,
        listState = listState,
        snackBarHostState = snackBarHostState,
        onBackClick = onBackClick,
        onEvent = viewModel::onEvent
    )
}
