package com.example.windimessenger.presentation.screen.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.windimessenger.presentation.theme.Typography

@Composable
fun MessagesScreen(
    onBackPressedListener: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "MessagesScreen",
            style = Typography.titleLarge
        )
    }
}