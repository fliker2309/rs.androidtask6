package com.example.androidtask6.ui.home.adapter

import com.example.androidtask6.data.entities.Song

interface SongActionListener {
    fun onSongClick(song: Song)
}