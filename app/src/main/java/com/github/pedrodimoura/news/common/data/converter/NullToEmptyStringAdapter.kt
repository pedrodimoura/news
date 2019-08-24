package com.github.pedrodimoura.news.common.data.converter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson

object NullToEmptyStringAdapter {

    @ToJson
    fun toJson(@NullToEmptyString value: String?): String? {
        return value
    }

    @FromJson
    @NullToEmptyString
    fun fromJson(reader: JsonReader): String? {
        val result = if (reader.peek() === JsonReader.Token.NULL) {
            reader.nextNull()
        } else {
            reader.nextString()
        }

        return result ?: ""
    }

}