package com.cattishapps.minka.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cattishapps.minka.data.converter.TypeConverter
import com.cattishapps.minka.data.dao.DayNoteDao
import com.cattishapps.minka.data.model.DayNoteEntity

@Database(entities = [DayNoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayNoteDao(): DayNoteDao
}