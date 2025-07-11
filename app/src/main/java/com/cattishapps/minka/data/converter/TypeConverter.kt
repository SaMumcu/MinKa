package com.cattishapps.minka.data.converter

import android.net.Uri
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
    @TypeConverter
    fun fromUriList(value: List<Uri>): String {
        return value.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun toUriList(value: String): List<Uri> {
        return if (value.isBlank()) emptyList()
        else value.split(",").map { Uri.parse(it) }
    }
}