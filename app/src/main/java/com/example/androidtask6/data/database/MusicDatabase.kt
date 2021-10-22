package com.example.androidtask6.data.database

import com.example.androidtask6.data.entities.Song
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.FileReader

class MusicDatabase {
    fun getAllSongs(jsonFileName: String): List<Song> {
        return parseWithGson(jsonFileName)
    }

    private fun parseWithGson(fileName: String): List<Song> {
        val fileReader = FileReader(fileName)

        val songListType = object : TypeToken<List<Song>>() {}.type

        val songs: List<Song> = Gson().fromJson(fileReader, songListType)

        fileReader.close()
        return songs
    }
}
