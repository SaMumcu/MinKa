package com.cattishapps.minka.di

import androidx.room.Room
import com.cattishapps.minka.data.repository.DayNoteRepository
import com.cattishapps.minka.db.AppDatabase
import com.cattishapps.minka.db.MIGRATION_1_2
import com.cattishapps.minka.domain.AddNoteUseCase
import com.cattishapps.minka.domain.DeleteNoteUseCase
import com.cattishapps.minka.domain.GetAllNotesUseCase
import com.cattishapps.minka.domain.GetNotesByDateUseCase
import com.cattishapps.minka.domain.UpdateNoteUseCase
import com.cattishapps.minka.ui.weekscreen.DayNoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "day_note_db"
        )
            .fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    single { get<AppDatabase>().dayNoteDao() }

    single { DayNoteRepository(get()) }

    single { GetNotesByDateUseCase(get()) }
    single { AddNoteUseCase(get()) }
    single { GetAllNotesUseCase(get()) }
    single { DeleteNoteUseCase(get()) }
    single { UpdateNoteUseCase(get()) }

    viewModel {
        DayNoteViewModel(
            getNotesByDateUseCase = get(),
            addNoteUseCase = get(),
            getAllNotesUseCase = get(),
            deleteNoteByIdUseCase = get(),
            updateNoteUseCase = get()
        )
    }
}
