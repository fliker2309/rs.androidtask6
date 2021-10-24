package com.example.androidtask6.exoplayer.callbacks

import android.support.v4.media.MediaMetadataCompat
import com.example.androidtask6.data.entities.Song

fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(
            it.title.toString(),
            it.subtitle.toString(),
            it.mediaUri.toString(),
            it.iconUri.toString()
        )
    }
}
