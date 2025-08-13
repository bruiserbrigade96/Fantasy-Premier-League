package com.example.fplassistant.ui.screens.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fplassistant.data.repository.FplRepository
import com.example.fplassistant.data.repository.UiPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayersViewModel(
    private val repository: FplRepository
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val query: String = "",
        val positionFilter: String? = null,
        val teamFilter: String? = null,
        val allPlayers: List<UiPlayer> = emptyList(),
        val visiblePlayers: List<UiPlayer> = emptyList(),
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
                _state.update { current ->
                    current.copy(
                        isLoading = false,
                        allPlayers = players,
                        visiblePlayers = applyFilters(players, current.query, current.positionFilter, current.teamFilter)
                    )
                }
            } catch (t: Throwable) {
                _state.update { it.copy(isLoading = false, error = t.message ?: "Unknown error") }
            }
        }
    }

    fun setQuery(query: String) {
        _state.update { current ->
            current.copy(
                query = query,
                visiblePlayers = applyFilters(current.allPlayers, query, current.positionFilter, current.teamFilter)
            )
        }
    }

    fun setPositionFilter(position: String?) {
        _state.update { current ->
            current.copy(
                positionFilter = position,
                visiblePlayers = applyFilters(current.allPlayers, current.query, position, current.teamFilter)
            )
        }
    }

    fun setTeamFilter(team: String?) {
        _state.update { current ->
            current.copy(
                teamFilter = team,
                visiblePlayers = applyFilters(current.allPlayers, current.query, current.positionFilter, team)
            )
        }
    }

    private fun applyFilters(
        players: List<UiPlayer>,
        query: String,
        position: String?,
        team: String?
    ): List<UiPlayer> {
        return players.asSequence()
            .filter { p -> query.isBlank() || p.name.contains(query, ignoreCase = true) || p.team.contains(query, ignoreCase = true) }
            .filter { p -> position == null || p.position.equals(position, ignoreCase = true) }
            .filter { p -> team == null || p.team.equals(team, ignoreCase = true) }
            .sortedWith(compareByDescending<UiPlayer> { it.totalPoints }.thenByDescending { it.form })
            .toList()
    }
}