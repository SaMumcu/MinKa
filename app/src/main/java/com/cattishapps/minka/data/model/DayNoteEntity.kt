package com.cattishapps.minka.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "day_note")
data class DayNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val note: String,
    val addedDate: LocalDate,
    val imageUris: List<Uri> = emptyList()
)
