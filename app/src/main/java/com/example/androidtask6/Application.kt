package com.example.androidtask6

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Application)
            modules(
                listOf(appModule, serviceModule)
            )
        }
    }
}

fun provideExoPlayer(
    context: Context,
    audioAttributes: AudioAttributes
) = SimpleExoPlayer.Builder(context).build().apply {
    setAudioAttributes(audioAttributes, true)
    setHandleAudioBecomingNoisy(true)
}

fun provideDataSourceFactory(
    context: Context
) = DefaultDataSourceFactory(context, Util.getUserAgent(context, "Music App"))