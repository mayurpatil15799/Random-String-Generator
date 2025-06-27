package com.example.randomstring.util

import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.randomstring.data.RandomStringEntity
import org.json.JSONObject
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ContentProviderHelper {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun queryProvider(context: Context, maxLength: Int): RandomStringEntity =
        withContext(Dispatchers.IO) {
            val uri = "content://com.iav.contestdataprovider/text".toUri()
            val bundle = Bundle().apply {
                putInt(ContentResolver.QUERY_ARG_LIMIT, maxLength)
            }
            val cursor = context.contentResolver.query(uri, null, bundle, null)
                ?: throw Exception("Provider returned null cursor")

            cursor.use {
                if (it.moveToFirst()) {
                    val jsonString = it.getString(it.getColumnIndexOrThrow("data"))
                    val json = JSONObject(jsonString).getJSONObject("randomText")
                    return@withContext RandomStringEntity(
                        value = json.getString("value"),
                        length = json.getInt("length"),
                        timestamp = json.getString("created")
                    )
                }
            }
            throw Exception("Failed to fetch from provider")
        }
}
