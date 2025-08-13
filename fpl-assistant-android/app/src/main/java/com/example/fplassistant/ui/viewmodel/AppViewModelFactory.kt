package com.example.fplassistant.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fplassistant.data.repository.FplRepository
import com.example.fplassistant.ui.screens.dashboard.DashboardViewModel
import com.example.fplassistant.ui.screens.players.PlayersViewModel

class AppViewModelFactory(
    private val repository: FplRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(PlayersViewModel::class.java) -> PlayersViewModel(repository) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}