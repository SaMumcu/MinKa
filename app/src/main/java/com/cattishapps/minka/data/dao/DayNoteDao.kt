package com.cattishapps.minka.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cattishapps.minka.data.model.DayNoteEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DayNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: DayNoteEntity)

    @Query("SELECT * FROM day_note WHERE date = :date")
    fun getNotesByDate(date: LocalDate): Flow<List<DayNoteEntity>>

    @Query("SELECT * FROM day_note")
    fun getAll(): Flow<List<DayNoteEntity>>

    @Query("DELETE FROM day_note")
    suspend fun clearAll()
}