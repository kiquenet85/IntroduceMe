package com.kiquenet.introduceme.ui.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author n.diazgranados
 */
@Module
public class NetworkModule {

    public NetworkModule() {
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
