package com.github.pedrodimoura.news.common.data.datasource.local.converter

import java.util.Date

class DateTypeConverter {

    @androidx.room.TypeConverter
    fun toLong(date: Date?): Long? = date?.time

    @androidx.room.TypeConverter
    fun toDate(timeInMillis: Long?): Date? = timeInMillis?.let { Date(it) }

}