package com.example.randomstring.di

import android.content.Context
import androidx.room.Room
import com.example.randomstring.data.RandomStringDao
import com.example.randomstring.data.RandomStringDatabase
import com.example.randomstring.repository.RandomStringRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AllModules {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RandomStringDatabase {
        return Room.databaseBuilder(
            context,
            RandomStringDatabase::class.java,
            "random_string_db"
        ).build()
    }

    @Provides
    fun provideDao(db: RandomStringDatabase): RandomStringDao  = db.randomStringDao()

    @Provides
    @Singleton
    fun provideRepository(dao: RandomStringDao, @ApplicationContext context: Context): RandomStringRepository {
        return RandomStringRepository(dao, context)
    }
}