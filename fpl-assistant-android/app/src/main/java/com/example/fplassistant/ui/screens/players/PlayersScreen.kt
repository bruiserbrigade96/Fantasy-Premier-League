@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.fplassistant.ui.screens.players

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fplassistant.data.repository.UiPlayer

@Composable
fun PlayersScreen(
    viewModelFactory: ViewModelProvider.Factory,
    onBack: () -> Unit
) {
    val vm: PlayersViewModel = viewModel(factory = viewModelFactory)
    val state by vm.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Players") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Back") }
                }
            )
        }
    ) { inner ->
        Box(Modifier.padding(inner)) {
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(
                    text = state.error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(Modifier.fillMaxSize().padding(12.dp)) {
                    SearchAndFilters(
                        query = state.query,
                        onQueryChange = vm::setQuery,
                        position = state.positionFilter,
                        onPositionChange = vm::setPositionFilter,
                        team = state.teamFilter,
                        onTeamChange = vm::setTeamFilter
                    )
                    Spacer(Modifier.height(8.dp))
                    PlayersList(players = state.visiblePlayers)
                }
            }
        }
    }
}

@Composable
private fun SearchAndFilters(
    query: String,
    onQueryChange: (String) -> Unit,
    position: String?,
    onPositionChange: (String?) -> Unit,
    team: String?,
    onTeamChange: (String?) -> Unit
) {
    Column {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search players or teams") }
        )
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = position == null,
                onClick = { onPositionChange(null) },
                label = { Text("All") }
            )
            listOf("GK", "DEF", "MID", "FWD").forEach { pos ->
                FilterChip(
                    selected = position == pos,
                    onClick = { onPositionChange(pos) },
                    label = { Text(pos) }
                )
            }
        }
    }
}

@Composable
private fun PlayersList(players: List<UiPlayer>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(players, key = { it.id }) { player ->
            PlayerRow(player)
        }
    }
}

@Composable
private fun PlayerRow(player: UiPlayer) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(text = "${player.name} • ${player.team}")
            Text(text = "${player.position} • £${"%.1f".format(player.price)}m")
            Text(text = "Pts: ${player.totalPoints}  Form: ${"%.1f".format(player.form)}  Sel%: ${"%.1f".format(player.selectedByPercent)}")
            if (player.status != "a") {
                Text(text = "Status: ${player.status}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}