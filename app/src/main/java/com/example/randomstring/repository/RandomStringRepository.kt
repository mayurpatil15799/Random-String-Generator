package com.example.randomstring.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.randomstring.data.RandomStringDao
import com.example.randomstring.data.RandomStringEntity
import com.example.randomstring.util.ContentProviderHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RandomStringRepository @Inject constructor(
    private val dao: RandomStringDao,
    private val context: Context
) {
    val allStrings: Flow<List<RandomStringEntity>> = dao.getAll()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun generateRandomString(maxLength: Int): Result<RandomStringEntity> {
        return try {
            val item = ContentProviderHelper.queryProvider(context, maxLength)
            dao.insert(item)
            Result.success(item)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteStringById(item: RandomStringEntity) {
        dao.delete(item)
    }

    suspend fun deleteAllStrings() {
        dao.deleteAll()
    }
}