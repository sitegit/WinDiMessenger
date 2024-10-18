package com.example.windimessenger.presentation.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.windimessenger.presentation.theme.Typography

@Composable
fun EditProfileScreen(
    paddingValues: PaddingValues,
    onBackPressedListener: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "EditProfileScreen",
            style = Typography.titleLarge
        )
    }
}