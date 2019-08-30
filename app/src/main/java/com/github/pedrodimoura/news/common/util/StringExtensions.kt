package com.github.pedrodimoura.news.common.util

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan

fun String.asItalic(): SpannableString {
    val spannableString = SpannableString(this)
    spannableString.setSpan(StyleSpan(Typeface.ITALIC), 0, length, 0)
    return spannableString
}