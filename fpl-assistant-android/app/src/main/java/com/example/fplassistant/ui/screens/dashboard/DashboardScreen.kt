@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.fplassistant.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fplassistant.data.repository.UiPlayer

@Composable
fun DashboardScreen(
    viewModelFactory: ViewModelProvider.Factory,
    onBrowsePlayers: () -> Unit
) {
    val vm: DashboardViewModel = viewModel(factory = viewModelFactory)
    val state by vm.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("FPL Assistant") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onBrowsePlayers) {
                Text("Browse Players")
            }
        }
    ) { inner ->
        Box(Modifier.padding(inner)) {
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(state.error ?: "", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    item { Text("Top Scorers", style = MaterialTheme.typography.titleMedium) }
                    items(state.topScorers) { player -> PlayerRow(player) }
                    item { Spacer(Modifier.height(8.dp)) }
                    item { Text("In Form", style = MaterialTheme.typography.titleMedium) }
                    items(state.inForm) { player -> PlayerRow(player) }
                    item { Spacer(Modifier.height(72.dp)) }
                }
            }
        }
    }
}

@Composable
private fun PlayerRow(player: UiPlayer) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(text = "${player.name} • ${player.team}")
            Text(text = "${player.position} • £${"%.1f".format(player.price)}m")
            Text(text = "Pts: ${player.totalPoints}  Form: ${"%.1f".format(player.form)}")
        }
    }
}