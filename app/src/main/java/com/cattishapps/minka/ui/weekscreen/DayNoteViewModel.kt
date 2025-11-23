package com.cattishapps.minka.ui.weekscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.domain.AddNoteUseCase
import com.cattishapps.minka.domain.DeleteNoteUseCase
import com.cattishapps.minka.domain.GetAllNotesUseCase
import com.cattishapps.minka.domain.GetNotesByDateUseCase
import com.cattishapps.minka.domain.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate

@KoinViewModel
class DayNoteViewModel(
    private val getNotesByDateUseCase: GetNotesByDateUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {

    private val _notes = MutableStateFlow<List<DayNoteEntity>>(emptyList())
    val notes: StateFlow<List<DayNoteEntity>> = _notes.asStateFlow()

    fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase().collect { notesList ->
                _notes.value = notesList
            }
        }
    }

    fun loadNotes(date: LocalDate) {
        viewModelScope.launch {
            getNotesByDateUseCase(date).collect { notesList ->
                _notes.value = notesList
            }
        }
    }

    fun addNote(note: DayNoteEntity) {
        viewModelScope.launch {
            addNoteUseCase(note)
            loadNotes(note.addedDate)
        }
    }

    fun deleteNoteById(noteId: Int) {
        viewModelScope.launch {
            deleteNoteByIdUseCase(noteId)
        }
    }

    fun updateNote(note: DayNoteEntity) {
        viewModelScope.launch {
            updateNoteUseCase(note)
        }
    }
}