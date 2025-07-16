package com.cattishapps.minka.data.repository

import com.cattishapps.minka.data.dao.DayNoteDao
import com.cattishapps.minka.data.model.DayNoteEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DayNoteRepository(private val dao: DayNoteDao) {

    fun getAllNotes(): Flow<List<DayNoteEntity>> = dao.getAll()

    fun getNotesByDate(date: LocalDate): Flow<List<DayNoteEntity>> = dao.getNotesByDate(date)

    suspend fun clearAll() = dao.clearAll()

    suspend fun addNote(note: DayNoteEntity) = dao.insertOrUpdate(note)

    suspend fun deleteNoteById(id: Int) = dao.deleteById(id)

    suspend fun updateNote(note: DayNoteEntity) = dao.updateNote(note)

}