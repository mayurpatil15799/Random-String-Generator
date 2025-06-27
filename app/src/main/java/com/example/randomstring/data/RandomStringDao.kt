package com.example.randomstring.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomStringDao {

    @Query("SELECT * FROM random_strings ORDER BY id DESC")
    fun getAll(): Flow<List<RandomStringEntity>>

    @Query("SELECT * FROM random_strings")
    suspend fun getAllOnce(): List<RandomStringEntity>

    @Query("SELECT * FROM random_strings")
    fun getAllFlow(): Flow<List<RandomStringEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(randomStringEntity: RandomStringEntity)

    @Delete
    suspend fun delete(randomStringEntity: RandomStringEntity)

    @Query("DELETE FROM random_strings")
    suspend fun deleteAll()

}