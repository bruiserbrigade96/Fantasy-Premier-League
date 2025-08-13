package com.example.fplassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import com.example.fplassistant.ui.navigation.AppNavHost
import com.example.fplassistant.ui.theme.FplAssistantTheme
import com.example.fplassistant.data.ServiceLocator
import com.example.fplassistant.ui.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = AppViewModelFactory(ServiceLocator.repository)
        setContent {
            FplAssistantTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavHost(viewModelFactory = viewModelFactory)
                }
            }
        }
    }
}