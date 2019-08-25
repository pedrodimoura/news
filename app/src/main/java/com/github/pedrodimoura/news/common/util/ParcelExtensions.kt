package com.github.pedrodimoura.news.common.util

import android.os.Parcel
import java.util.Date

fun Parcel.readDate(): Date? {
    return if (readBool())
        readNonNullDate()
    else
        null
}

fun Parcel.writeDate(value: Date?) {
    if (value != null) {
        writeBool(true)
        writeNonNullDate(value)
    } else {
        writeBool(false)
    }
}

fun Parcel.readBool() = this.readByte() != 0.toByte()

fun Parcel.writeBool(value: Boolean) {
    this.writeByte(if (value) 1 else 0)
}

fun Parcel.readNonNullDate() = Date(this.readLong())

fun Parcel.writeNonNullDate(value: Date) = this.writeLong(value.time)

fun Parcel.readStringOrEmpty():String = readString() ?: ""