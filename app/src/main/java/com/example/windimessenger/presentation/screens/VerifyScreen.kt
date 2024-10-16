package com.example.windimessenger.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.windimessenger.presentation.theme.Typography
import com.example.windimessenger.presentation.utils.InputDescription

@Composable
fun VerifyScreen(
    phoneNumber: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.Center
    ) {
        InputDescription(title = "Введите код", style = Typography.titleLarge)
        InputDescription(
            title = "Мы отправили SMS с кодом проверки на Ваш телефон $phoneNumber",
            style = Typography.bodyLarge
        )
        OtpTextField()
    }
}

@Composable
private fun ColumnScope.OtpTextField() {

    var otpValue by rememberSaveable { mutableStateOf("") }

    BasicTextField(
        value = otpValue,
        onValueChange = {
            if (it.length <= 6) {
                otpValue = it
            }
        },
        modifier = Modifier.align(Alignment.CenterHorizontally),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(6) { index ->
                    val char = otpValue.getOrNull(index)?.toString() ?: ""

                    OtpTextFieldElement(char)

                    if (index < 5) {
                        Log.i("MyTag", index.toString())
                        Spacer(modifier = Modifier.width(8.dp)) }
                }
            }
        }
    )
}

@Composable
private fun OtpTextFieldElement(
    char: String
) {
    val borderColor = if (char.isNotEmpty()) Color.Blue else Color.LightGray

    Box(
        modifier = Modifier
            .size(50.dp)
            .border(
                1.dp,
                borderColor,
                RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            style = Typography.titleLarge,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}