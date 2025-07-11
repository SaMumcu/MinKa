package com.cattishapps.minka.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cattishapps.minka.data.converter.TypeConverter
import com.cattishapps.minka.data.dao.DayNoteDao
import com.cattishapps.minka.data.model.DayNoteEntity

@Database(entities = [DayNoteEntity::class], version = 2, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayNoteDao(): DayNoteDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE day_note ADD COLUMN imageUris TEXT NOT NULL DEFAULT ''")
    }
}