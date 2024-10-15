package com.example.windimessenger.presentation.utils

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlin.math.absoluteValue

class NumberMask(private val countryIso: String, private val context: Context): VisualTransformation {

    private val phoneNumberUtil = PhoneNumberUtil.createInstance(context)
    private val mask = getPhoneNumberMask(countryIso)
    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    private fun getPhoneNumberMask(countryCode: String): String {
        return try {
            val exampleNumber = phoneNumberUtil.getExampleNumberForType(
                countryCode, PhoneNumberUtil.PhoneNumberType.MOBILE
            )
            val formattedNumber = phoneNumberUtil.format(exampleNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
            val mask = formattedNumber.replace("\\d".toRegex(), "#")

            if (mask.startsWith("# "))  mask.substring(2) else mask
        } catch (e: Exception) {
            Log.e("NumberMask", "Error getting example number for country code $countryCode", e)
            "##########"
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}