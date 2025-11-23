package com.cattishapps.minka.domain

import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.data.repository.DayNoteRepository

class AddNoteUseCase(
    private val repository: DayNoteRepository
) {
    suspend operator fun invoke(note: DayNoteEntity) {
        repository.addNote(note)
    }
}