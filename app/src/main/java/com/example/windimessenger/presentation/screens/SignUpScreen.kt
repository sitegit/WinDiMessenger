package com.example.windimessenger.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.windimessenger.presentation.theme.Typography

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val message = remember{ mutableStateOf("") }
        TextField(
            value = message.value,
            textStyle = TextStyle(fontSize=25.sp),
            onValueChange = {newText -> message.value = newText}
        )
    }
}