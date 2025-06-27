package com.example.randomstring.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RandomStringEntity::class], version = 1, exportSchema = false)
abstract class RandomStringDatabase : RoomDatabase() {
    abstract fun randomStringDao(): RandomStringDao

    companion object {
        @Volatile
        private var INSTANCE: RandomStringDatabase? = null

        fun getDatabase(context: Context): RandomStringDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RandomStringDatabase::class.java,
                    "random_string_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}