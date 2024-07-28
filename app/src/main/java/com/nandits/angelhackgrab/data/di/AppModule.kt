package com.nandits.angelhackgrab.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Stable
import com.nandits.angelhackgrab.AngelHackGrabApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Stable
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): AngelHackGrabApp {
        return app as AngelHackGrabApp
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("GREFF", Context.MODE_PRIVATE)
    }
}