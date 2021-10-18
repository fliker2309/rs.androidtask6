package com.example.androidtask6.di

import android.content.Context
import coil.Coil
import coil.request.CachePolicy
import com.example.androidtask6.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoilInstance(
        @ApplicationContext context: Context
    ) = Coil.imageLoader(context).newBuilder()
        .placeholder(R.drawable.ic_image)
        .error(R.drawable.ic_image)
        .diskCachePolicy(CachePolicy.ENABLED)
}
