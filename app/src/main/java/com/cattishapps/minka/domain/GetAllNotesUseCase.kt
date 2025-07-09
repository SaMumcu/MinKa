package com.cattishapps.minka.domain

import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.data.repository.DayNoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(
    private val repository: DayNoteRepository
) {
    operator fun invoke(): Flow<List<DayNoteEntity>> {
        return repository.getAllNotes()
    }
}