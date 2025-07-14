package com.cattishapps.minka.domain

import com.cattishapps.minka.data.repository.DayNoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: DayNoteRepository
) {
    suspend operator fun invoke(noteId: Int) {
        repository.deleteNoteById(noteId)
    }
}