package com.cattishapps.minka.domain

import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.data.repository.DayNoteRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetNotesByDateUseCase(
    private val repository: DayNoteRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<DayNoteEntity>> {
        return repository.getNotesByDate(date)
    }
}