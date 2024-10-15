package com.example.windimessenger.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.windimessenger.presentation.navigation.rememberNavigationState
import com.example.windimessenger.presentation.screens.AuthScreen
import com.example.windimessenger.presentation.screens.WinDiMainScreen
import com.example.windimessenger.presentation.theme.WinDiMessengerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val authState = MutableStateFlow(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            delay(3000)
            //authState.value = false
        }

        setContent {
            WinDiMessengerTheme {
                val navigateState = rememberNavigationState()
                val currentAuthState by authState.collectAsState()

                when (currentAuthState) {
                    false -> { AuthScreen(navigateState) }
                    true -> { WinDiMainScreen(navigateState) }
                }
            }
        }
    }
}

