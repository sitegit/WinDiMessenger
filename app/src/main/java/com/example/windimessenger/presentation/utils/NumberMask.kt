package com.example.windimessenger.presentation.utils

import android.content.Context
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

            val nationalNumber = phoneNumberUtil.getNationalSignificantNumber(exampleNumber)

            val formattedNumber = phoneNumberUtil.format(exampleNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
            trimToMatch(nationalNumber, formattedNumber).replace("\\d".toRegex(), "#")
        } catch (e: Exception) {
            "##########"
        }
    }

    fun getNumberLength() = mask.count { it == '#' }

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

    private fun trimToMatch(nationalNumber: String, formattedNumber: String): String {
        var str = ""
        val nationalNumberLength = nationalNumber.length
        var count = 0
        var nIndex = nationalNumber.length - 1

        for (i in formattedNumber.length - 1 downTo 0) {
            if (count == nationalNumberLength && formattedNumber[i] == '(') {
                str += formattedNumber[i]
                break
            } else if (count == nationalNumberLength) {
                break
            }

            if (formattedNumber[i] != nationalNumber[nIndex]) {
                str += formattedNumber[i]
            } else {
                str += formattedNumber[i]
                nIndex--
                count++
            }
        }

        return str.reversed()
    }
}