package com.example.windimessenger.presentation.screens.login

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.screens.login.components.CountryCodePicker
import com.example.windimessenger.presentation.theme.InputDescription
import com.example.windimessenger.presentation.theme.Typography
import com.example.windimessenger.presentation.theme.showToast
import com.example.windimessenger.presentation.utils.Country
import com.example.windimessenger.presentation.utils.NumberMask
import com.example.windimessenger.presentation.utils.NumberValidator
import java.util.Locale

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory()),
    onLoginClick: (String) -> Unit
) {
    val state: LoginState by viewModel.uiState.collectAsState()
    val context: Context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().imePadding(),
        verticalArrangement = Arrangement.Center
    ) {
        CountryContent(isLoad = state == LoginState.Loading){
            viewModel.sendNumber(it)
        }

        when (val currentState = state) {
            is LoginState.Idle, LoginState.Loading -> {}
            is LoginState.Success -> {
                onLoginClick(currentState.phone)
                viewModel.resetState()
            }
            is LoginState.Error -> {
                showToast(context, "Не удалось отправить номер, проверьте подключение к интернету")
            }
        }
    }
}

@Composable
fun CountryContent(
    isLoad: Boolean,
    onLoginClick: (String) -> Unit
) {
    val context: Context = LocalContext.current
    var number: String by rememberSaveable { mutableStateOf("") }
    var country: Country by rememberSaveable {
        mutableStateOf(
            Country.getCountryByIso(Locale.getDefault().country) ?: Country.RussianFederation
        )
    }

    val validatePhoneNumber: NumberValidator = remember { NumberValidator(context = context) }

    var isNumberValid: Boolean by rememberSaveable {
        mutableStateOf(
            validatePhoneNumber(number = number, countryCode = country.countryCode)
        )
    }

    val numberMask: NumberMask = NumberMask(countryIso = country.countryIso, context = context)

    InputDescription(title = "Телефон", style = Typography.titleLarge)
    InputDescription(title = "Проверьте код страны и введите свой номер телефона", style = Typography.bodyLarge)

    OutlinedTextField(
        value = number,
        onValueChange = {
            val filteredText = it.filter { ch -> ch.isDigit() }

            if (it.length <= numberMask.getNumberLength()) {
                number = filteredText

                isNumberValid = validatePhoneNumber(
                    number = it, countryCode = country.countryCode
                )
            }
        },
        modifier = Modifier.fillMaxWidth().padding(16.dp, 5.dp),
        textStyle = Typography.bodyMedium,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = "Введите номер телефона", style = Typography.bodyMedium
            )
        },
        label = {
            Text(
                text = "Номер телефона", style = Typography.bodyMedium
            )
        },
        leadingIcon = {
            CountryCodePicker(
                selectedCountry = country,
                countryList = Country.getAllCountries(),
                onCountrySelected = {
                    country = it
                    isNumberValid = validatePhoneNumber(
                        number = number, countryCode = it.countryCode
                    )
                }
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        ),
        isError = !isNumberValid && number.isNotEmpty(),
        visualTransformation = numberMask,
    )

    NavigateFloatButton(
        isLoad = isLoad,
        phoneNumber = "${country.countryCode}$number",
        isNumberValid = isNumberValid,
        onLoginClick = onLoginClick
    )
}

@Composable
private fun NavigateFloatButton(
    isLoad: Boolean,
    phoneNumber: String,
    isNumberValid: Boolean,
    onLoginClick: (String) -> Unit
) {
    val context: Context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        FloatingActionButton(
            onClick = {
                if (isNumberValid)
                    onLoginClick(phoneNumber)
                else
                    showToast(context,"Неверный формат номера телефона")
            },
            modifier = Modifier.padding(top = 64.dp, end = 16.dp)
        ) {
            if (!isLoad)
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            else
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.DarkGray)
        }
    }
}


