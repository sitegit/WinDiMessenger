package com.example.windimessenger.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.windimessenger.domain.entity.network.AuthState
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.navigation.rememberNavigationState
import com.example.windimessenger.presentation.screen.AuthDestination
import com.example.windimessenger.presentation.screen.WinDiMainDestination
import com.example.windimessenger.presentation.theme.WinDiMessengerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val component = getApplicationComponent()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
            val authState = viewModel.authState.collectAsState(initial = AuthState.Initial)

            WinDiMessengerTheme {
                val navigateState = rememberNavigationState()

                when (authState.value) {
                    is AuthState.NotAuthorized -> { AuthDestination(navigateState) }
                    is AuthState.Authorized -> { WinDiMainDestination(navigateState) }
                    AuthState.Initial -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) { LinearProgressIndicator() }
                    }
                }
            }
        }
    }
}

