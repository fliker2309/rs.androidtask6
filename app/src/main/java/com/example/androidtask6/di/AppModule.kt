package com.example.androidtask6.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidtask6.R
import com.example.androidtask6.data.database.MusicDatabase
import com.example.androidtask6.exoplayer.MusicService
import com.example.androidtask6.exoplayer.MusicServiceConnection
import com.example.androidtask6.exoplayer.MusicSource
import com.example.androidtask6.presentation.MainViewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { MusicSource(get()) }

    single { MusicServiceConnection(get()) }

    single {
        Glide.with(get<Context>()).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        )
    }

    viewModel { MainViewModel(get()) }
}

val serviceModule = module {
    single { MusicDatabase(get()) }

    single {
        SimpleExoPlayer.Builder(get()).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(), true
            )
            setHandleAudioBecomingNoisy(true)
        }
    }

    single { DefaultDataSourceFactory(get(), Util.getUserAgent(get(), "Music App")) }
}