package com.example.windimessenger.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.windimessenger.presentation.theme.Typography
import com.example.windimessenger.presentation.utils.InputDescription
import com.example.windimessenger.presentation.utils.NumberMask
import com.example.windimessenger.presentation.utils.NumberValidator
import java.util.Locale

@Composable
fun LoginScreen(
    onLoginClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().imePadding(),
        verticalArrangement = Arrangement.Center
    ) {
        CountryContent { onLoginClick(it) }
    }
}

@Composable
fun CountryContent(
    onLoginClick: (String) -> Unit
) {

    val context = LocalContext.current
    var number by rememberSaveable { mutableStateOf("") }

    var country by rememberSaveable {
        mutableStateOf(
            Country.getCountryByIso(Locale.getDefault().country) ?: Country.RussianFederation
        )
    }

    InputDescription(title = "Телефон", style = Typography.titleLarge)
    InputDescription(title = "Проверьте код страны и введите свой номер телефона", style = Typography.bodyLarge)

    val validatePhoneNumber = remember { NumberValidator(context = context) }

    var isNumberValid: Boolean by rememberSaveable {
        mutableStateOf(
           validatePhoneNumber(number = number, countryCode = country.countryCode)
        )
    }

    val numberMask = NumberMask(countryIso = country.countryIso, context = context)

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
        textStyle = Typography.labelSmall,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = "Введите номер телефона", style = Typography.labelSmall
            )
        },
        label = {
            Text(
                text = "Номер телефона", style = Typography.labelSmall
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
        phoneNumber = "${country.countryCode}$number",
        isNumberValid = isNumberValid,
        onLoginClick = onLoginClick
    )
}

@Composable
private fun NavigateFloatButton(
    phoneNumber: String,
    isNumberValid: Boolean,
    onLoginClick: (String) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        FloatingActionButton(
            onClick = {
                if (isNumberValid)
                    onLoginClick(phoneNumber)
                else
                    Toast.makeText(context, "Неверный формат номера телефона", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .padding(top = 64.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowRight, contentDescription = null)
        }
    }
}


