package com.example.windimessenger.presentation.screen.login

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.theme.Typography
import com.example.windimessenger.presentation.theme.InputDescription
import com.example.windimessenger.presentation.theme.showToast

@Composable
fun VerifyScreen(
    viewModel: LoginViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory()),
    phoneNumber: String,
    onNavigateRegister: () -> Unit
) {
    val state: LoginState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

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
        OtpTextField {
            viewModel.checkAuthUser(phoneNumber, it)
        }
        Box(
            modifier = Modifier.fillMaxWidth().height(20.dp).padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state is LoginState.Loading) {
                LinearProgressIndicator()
            }
        }

        when (val currentState = state) {
            is LoginState.Error -> showToast(context, currentState.message)
            is LoginState.Success -> {
                if (currentState.isValidAuthCode) viewModel.checkAuth() else onNavigateRegister()
            }
            else -> {}
        }
    }
}

@Composable
private fun ColumnScope.OtpTextField(
    onOtpComplete: (String) -> Unit
) {

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
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                Log.i("okHttp", "1isfdfd")
                if (otpValue.length == 6) onOtpComplete(otpValue)
            }
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