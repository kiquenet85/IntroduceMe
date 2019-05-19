package com.kiquenet.introduceme.ui.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

/**
 * @author n.diazgranados
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }
}
