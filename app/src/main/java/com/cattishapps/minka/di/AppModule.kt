package com.cattishapps.minka.di

import android.content.Context
import androidx.room.Room
import com.cattishapps.minka.data.dao.DayNoteDao
import com.cattishapps.minka.data.repository.DayNoteRepository
import com.cattishapps.minka.db.AppDatabase
import com.cattishapps.minka.domain.AddNoteUseCase
import com.cattishapps.minka.domain.GetAllNotesUseCase
import com.cattishapps.minka.domain.GetNotesByDateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesLocalDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "day_note_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesDayNoteDao(db: AppDatabase): DayNoteDao {
        return db.dayNoteDao()
    }

    @Provides
    @Singleton
    fun providesDayNoteRepository(
        dao: DayNoteDao
    ): DayNoteRepository {
        return DayNoteRepository(dao)
    }

    @Provides
    @Singleton
    fun providesGetNotesByDateUseCase(
        repository: DayNoteRepository
    ): GetNotesByDateUseCase {
        return GetNotesByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesAddNoteUseCase(
        repository: DayNoteRepository
    ): AddNoteUseCase {
        return AddNoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllNotesUseCase(
        repository: DayNoteRepository
    ): GetAllNotesUseCase {
        return GetAllNotesUseCase(repository)
    }
}