package com.example.windimessenger.presentation.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CountryCodePicker(
    modifier: Modifier = Modifier,
    selectedCountry: Country = Country.RussianFederation,
    countryList: List<Country> = Country.getAllCountries(),
    onCountrySelected: (Country) -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textStyle: TextStyle = TextStyle(),
    itemPadding: Int = 10,
) {

    var country by remember { mutableStateOf(selectedCountry) }
    var isPickerOpen by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable {
            isPickerOpen = true
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CountryView(
            country = country,
            showFlag = true,
            showCountryIso = false,
            showCountryName = false,
            showCountryCode = true,
            showArrow = false,
            textStyle = textStyle,
            itemPadding = itemPadding,
            clipToFull = false
        )

        if (isPickerOpen) {
            CountryPickerBottomSheet(modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
                onDismissRequest = { isPickerOpen = false },
                onItemClicked = {
                    onCountrySelected(it)
                    country = it
                    isPickerOpen = false

                },
                textStyle = textStyle,
                listOfCountry = countryList,
                itemPadding = itemPadding,
                backgroundColor = backgroundColor
            )
        }

    }

}