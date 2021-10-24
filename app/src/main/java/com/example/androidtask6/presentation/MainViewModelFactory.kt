package com.example.androidtask6.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtask6.exoplayer.MusicServiceConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModelFactory @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MainViewModel::class.java -> MainViewModel(musicServiceConnection)
        else -> throw IllegalArgumentException("$modelClass is not registered viewModel")
    } as T
}
