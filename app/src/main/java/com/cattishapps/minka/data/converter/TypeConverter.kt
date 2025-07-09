package com.cattishapps.minka.data.converter

import androidx.room.TypeConverter
import java.time.LocalDate

class TypeConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }
}