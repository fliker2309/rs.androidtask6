package com.example.androidtask6

import android.app.Application
import com.example.androidtask6.di.appModule
import com.example.androidtask6.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

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