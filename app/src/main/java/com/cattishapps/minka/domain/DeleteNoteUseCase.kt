package com.cattishapps.minka.domain

import com.cattishapps.minka.data.repository.DayNoteRepository

class DeleteNoteUseCase(
    private val repository: DayNoteRepository
) {
    suspend operator fun invoke(noteId: Int) {
        repository.deleteNoteById(noteId)
    }
}