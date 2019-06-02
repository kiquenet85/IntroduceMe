package com.kiquenet.introduceme.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kiquenet.introduceme.di.scope.ApplicationContext
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoUi
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoUiDeserializer
import com.kiquenet.introduceme.network.NetworkManager
import com.kiquenet.introduceme.settings.Settings
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
    fun provideGson(@ApplicationContext appContext: Context): Gson {
        val gsonBuilder = GsonBuilder()
            .registerTypeAdapter(UserInfoUi::class.java, UserInfoUiDeserializer(appContext))
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideNetworkManger(@ApplicationContext appContext: Context, settings: Settings): NetworkManager {
        return NetworkManager(appContext, settings)
    }
}
