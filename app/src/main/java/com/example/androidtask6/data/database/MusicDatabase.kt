package com.example.androidtask6.data.database

import android.content.Context
import com.example.androidtask6.R
import com.example.androidtask6.data.entities.Song
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MusicDatabase(
    private val context: Context
) {

    fun getPlaylist(): List<Song>? {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val listType = Types.newParameterizedType(List::class.java, Song::class.java)
        val adapter: JsonAdapter<List<Song>> = moshi.adapter(listType)

        val jsonPlaylist = context.resources.openRawResource(R.raw.playlist)
            .bufferedReader().use { it.readText() }

        return adapter.fromJson(jsonPlaylist)
    }
}
