package com.example.windimessenger.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.windimessenger.presentation.theme.Typography
import com.rejowan.ccpc.CCPUtils
import com.rejowan.ccpc.CCPValidator
import com.rejowan.ccpc.Country
import com.rejowan.ccpc.CountryCodePicker

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        ShowCCPWithTextField { onLoginClick() }
        NavigateFloatButton(
            onLoginClick = onLoginClick
        )
    }
}

@Composable
fun ShowCCPWithTextField(
    onLoginClick: () -> Unit
) {

    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    var country by remember { mutableStateOf(Country.RussianFederation) }

    CountryDetection { country = it }
    PhoneInputDescription(title = "Телефон", style = Typography.titleLarge)
    PhoneInputDescription(title = "Проверьте код страны и введите свой номер телефона", style = Typography.bodyLarge)

    val validatePhoneNumber = remember(context) { CCPValidator(context = context) }

    var isNumberValid: Boolean by rememberSaveable(country, text) {
        mutableStateOf(
            validatePhoneNumber(number = text, countryCode = country.countryCode)
        )
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            isNumberValid = validatePhoneNumber(
                number = it, countryCode = country.countryCode
            )

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
                        number = text, countryCode = it.countryCode
                    )
                }
            )
        },
        isError = !isNumberValid && text.isNotEmpty(),
        //visualTransformation = CCPTransformer(context, country.countryIso),
    )
}

@Composable
private fun CountryDetection(
    countryListener: (Country) -> Unit
) {
    if (!LocalInspectionMode.current) {
        CCPUtils.getCountryAutomatically(context = LocalContext.current).let {
            it?.let { countryListener(it) }
        }
    }
}

@Composable
private fun ColumnScope.NavigateFloatButton(
    onLoginClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onLoginClick,
        modifier = Modifier
            .align(Alignment.End)
            .padding(top = 48.dp, end = 16.dp)
    ) {
        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
    }
}

@Composable
private fun PhoneInputDescription(
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

