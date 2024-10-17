package com.example.windimessenger.presentation.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.theme.Typography

@Composable
fun SignUpScreen(
    phoneNumber: String,
    viewModel: SignUpViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = "Регистрация", style = Typography.titleLarge)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            var name by rememberSaveable { mutableStateOf("") }
            var username by rememberSaveable { mutableStateOf("") }

            RegistrationTextField(text = name, label = "Введите имя") { name = it }
            RegistrationTextField(text = username, label = "Введите никнейм", isUserName = true) { username = it }
            RegisterButton(name = name, username = username) {
                viewModel.registerUser(phoneNumber, name, username)
            }
        }
    }
}

@Composable
private fun RegisterButton(
    name: String,
    username: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        enabled = name.length > 2 && username.length > 4,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp).height(50.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Войти", style = Typography.titleLarge)
    }
}

@Composable
private fun RegistrationTextField(
    text: String,
    label: String,
    isUserName: Boolean = false,
    onChangedText: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        textStyle = Typography.bodyMedium,
        onValueChange = { newText ->
            if (isUserName) {
                onChangedText(validateInput(newText))
            } else {
                onChangedText(newText)
            }
        },
        modifier = Modifier.fillMaxWidth().padding(16.dp, 4.dp),
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        label = {
            Text(
                text = label, style = Typography.bodyMedium
            )
        }
    )
}

private fun validateInput(text: String): String {
    return text.filter {
        it in 'a'..'z' || it in 'A'..'Z' || it.isDigit() || it == '-' || it == '_'
    }
}
