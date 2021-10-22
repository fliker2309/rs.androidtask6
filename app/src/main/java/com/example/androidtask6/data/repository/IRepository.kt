package com.example.androidtask6.data.repository

import com.example.androidtask6.data.entities.Song

interface IRepository {
    val songs: List<Song>
}
