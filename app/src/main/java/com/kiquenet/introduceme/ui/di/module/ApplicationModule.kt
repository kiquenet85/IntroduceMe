package com.kiquenet.introduceme.ui.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.ui.di.scope.ApplicationContext
import com.kiquenet.introduceme.ui.network.NetworkManager
import com.kiquenet.introduceme.ui.settings.Settings
import com.kiquenet.introduceme.ui.util.FileUtil
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

/**
 * Provide application-level dependencies
 * @author n.diazgranados
 */
@Module
class ApplicationModule(protected val application: Application) {

    @Provides
    @ApplicationContext
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun settings(@ApplicationContext context: Context): Settings {
        return Gson().fromJson(FileUtil.readFile(context, R.raw.default_config), Settings::class.java)
    }

    @Provides
    @Singleton
    internal fun provideNetworkManger(@ApplicationContext context: Context, settings: Settings): NetworkManager {
        return NetworkManager(context, settings)
    }
}
