package com.kiquenet.introduceme.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.di.scope.ApplicationContext
import com.kiquenet.introduceme.settings.Settings
import com.kiquenet.introduceme.util.FileUtil
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
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun settings(@ApplicationContext context: Context, gson: Gson): Settings {
        return gson.fromJson(FileUtil.readFile(context, R.raw.default_config), Settings::class.java)
    }
}
