package com.example.windimessenger.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.windimessenger.data.model.AuthCodeRequestDto
import com.example.windimessenger.data.network.ApiFactory
import com.example.windimessenger.presentation.navigation.rememberNavigationState
import com.example.windimessenger.presentation.screens.AuthDestination
import com.example.windimessenger.presentation.screens.WinDiMainDestination
import com.example.windimessenger.presentation.theme.WinDiMessengerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val authState = MutableStateFlow(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        lifecycleScope.launch {
            delay(3000)
            //authState.value = false

//            try {
//                val result = ApiFactory.apiService.sendAuthCode(AuthCodeRequestDto("+9100000000"))
//                Log.i("MyTag", result.isSuccess.toString())
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    // Обработка ошибки
//                    Log.i("MyTag","Error: ${e.message}")
//                }
//            }
        }

        setContent {
            WinDiMessengerTheme {
                val navigateState = rememberNavigationState()
                val currentAuthState by authState.collectAsState()

                when (currentAuthState) {
                    false -> { AuthDestination(navigateState) }
                    true -> { WinDiMainDestination(navigateState) }
                }
            }
        }
    }
}

