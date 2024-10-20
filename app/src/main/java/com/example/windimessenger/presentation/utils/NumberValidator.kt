package com.example.windimessenger.presentation.utils

import android.content.Context
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

class NumberValidator(private val context: Context) {

    private val phoneUtil: PhoneNumberUtil by lazy { PhoneNumberUtil.createInstance(context) }

    operator fun invoke(number: String, countryCode: String): Boolean {
        //   Log.e("MyTag", "Number: $number, CountryCode: $countryCode" )
        val fullNumber = "$countryCode$number"
        return try {
            val phoneNumber = phoneUtil.parse(fullNumber, null)
            phoneUtil.isValidNumber(phoneNumber)
        } catch (e: Exception) {
            false
        }
    }
}
