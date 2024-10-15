package com.example.windimessenger.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.windimessenger.presentation.theme.Typography

@Composable
fun ChatsScreen(
    onMessagesClickListener: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.clickable {
                onMessagesClickListener()
            },
            text = "ChatsScreen",
            style = Typography.titleLarge
        )
    }
}