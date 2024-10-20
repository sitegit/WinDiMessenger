package com.example.windimessenger.presentation.screen.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.windimessenger.R
import com.example.windimessenger.presentation.utils.Country

@Composable
fun CountrySearch(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    showClearIcon: Boolean = true,
    requestFocus: Boolean = false,
    onFocusChanged: (FocusState) -> Unit = {}
) {

    val requester = remember {
        FocusRequester()
    }
    LaunchedEffect(Unit) {
        if (requestFocus) {
            requester.requestFocus()
        } else {
            requester.freeFocus()
        }
    }

    TextField(modifier = Modifier
        .fillMaxWidth()
        .focusRequester(requester)
        .onFocusChanged { onFocusChanged(it) },
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = textStyle,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(text = hint)
        },
        leadingIcon = {
            Icon(Icons.Outlined.Search, contentDescription = stringResource(R.string.search))
        },
        trailingIcon = {
            if (showClearIcon && value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(Icons.Outlined.Clear, contentDescription = stringResource(R.string.clear))
                }
            }
        }

    )
}


@Composable
fun CountryUI(
    country: Country,
    onCountryClicked: () -> Unit,
    showCountryFlag: Boolean = true,
    showCountryIso: Boolean = false,
    showCountryCode: Boolean = true,
    countryTextStyle: TextStyle,
    itemPadding: Int = 10

) {

    Row(
        Modifier
            .clickable(onClick = { onCountryClicked() })
            .padding(itemPadding.dp, (itemPadding * 1.5).dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically

    ) {

        val countryString = if (showCountryFlag && showCountryIso) {
            (getEmojiFlag(country.countryIso)) + "  " + country.countryName + "  (" + country.countryIso + ")"
        } else if (showCountryFlag) {
            (getEmojiFlag(country.countryIso)) + "  " + country.countryName
        } else if (showCountryIso) {
            country.countryName + "  (" + country.countryIso + ")"
        } else {
            country.countryName
        }

        Text(
            text = countryString,
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            style = countryTextStyle,
            overflow = TextOverflow.Ellipsis
        )

        if (showCountryCode) {
            Text(
                text = country.countryCode, style = countryTextStyle
            )
        }

    }


}

@Composable
fun CountryView(
    country: Country,
    textStyle: TextStyle,
    showFlag: Boolean,
    showCountryCode: Boolean,
) {

    Row(
        modifier = Modifier.padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (showFlag) {
            Text(
                text = getEmojiFlag(country.countryIso),
                modifier = Modifier.padding(start = 5.dp, end = 10.dp),
                style = textStyle
            )
        }

        if (showCountryCode) {
            Text(
                text = country.countryCode,
                modifier = Modifier.padding(end = 5.dp),
                fontWeight = FontWeight.Bold,
                style = textStyle
            )
        }

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
        )
    }


}

fun getEmojiFlag(isoString: String): String {
    return isoString.uppercase().map { char -> Character.codePointAt("$char", 0) + 0x1F1A5 }
        .joinToString("") {
            String(Character.toChars(it))
        }
}