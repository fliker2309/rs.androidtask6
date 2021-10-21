package com.example.androidtask6.data

import com.example.androidtask6.data.entities.Song
import com.example.androidtask6.data.json.SongsJson

internal fun SongsJson.toSong(): Song {
    return Song(
        title = title,
        artist = artist,
        bitmapUri = bitmapUri,
        trackUri = trackUri,
        duration = duration
    )
}
