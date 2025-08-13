package com.example.fplassistant.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fplassistant.data.repository.FplRepository
import com.example.fplassistant.data.repository.UiPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: FplRepository
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val topScorers: List<UiPlayer> = emptyList(),
        val inForm: List<UiPlayer> = emptyList(),
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState(isLoading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val players = repository.getPlayersUi()
                val topScorers = players.sortedByDescending { it.totalPoints }.take(5)
                val inForm = players.sortedByDescending { it.form }.take(5)
                _state.update { it.copy(isLoading = false, topScorers = topScorers, inForm = inForm) }
            } catch (t: Throwable) {
                _state.update { it.copy(isLoading = false, error = t.message ?: "Unknown error") }
            }
        }
    }
}