package com.kiquenet.introduceme.ui.di.module.Activity

import android.app.Activity
import android.content.Context
import com.kiquenet.introduceme.ui.di.scope.PerActivity
import dagger.Module
import dagger.Provides

/**
 * @author n.diazgranados
 */
@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @PerActivity
    fun provideContext(): Context {
        return activity
    }
}
