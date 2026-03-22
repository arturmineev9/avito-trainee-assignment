package ru.arturmineev9.avitotraineeassignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.arturmineev9.avitotraineeassignment.core.navigation.AppGraph
import ru.arturmineev9.avitotraineeassignment.feature.auth.api.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _startDestination = MutableStateFlow<AppGraph>(AppGraph.AuthGraph)
    val startDestination = _startDestination.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()

            if (user != null) {
                _startDestination.value = AppGraph.ChatsGraph
            } else {
                _startDestination.value = AppGraph.AuthGraph
            }

            _isLoading.value = false
        }
    }
}
