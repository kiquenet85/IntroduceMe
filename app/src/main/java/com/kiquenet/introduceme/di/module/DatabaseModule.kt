package com.kiquenet.introduceme.di.module

import android.content.Context
import com.kiquenet.introduceme.data.db.AppDatabase
import com.kiquenet.introduceme.di.scope.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author n.diazgranados
 */
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }
}
