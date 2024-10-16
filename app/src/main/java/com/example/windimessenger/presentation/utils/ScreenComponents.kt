package com.example.windimessenger.presentation.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InputDescription(
    title: String,
    style: TextStyle
) {
    Text(
        text = title,
        style = style,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(8.dp).fillMaxWidth()
    )
}