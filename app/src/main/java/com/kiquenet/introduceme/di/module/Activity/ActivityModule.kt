package com.kiquenet.introduceme.di.module.Activity

import android.app.Activity
import android.content.Context
import com.kiquenet.introduceme.di.scope.PerActivity
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference

/**
 * @author n.diazgranados
 */
@Module
class ActivityModule(private val activity: WeakReference<Activity?>) {

    @Provides
    @PerActivity
    fun provideActivity(): WeakReference<Activity?> {
        return activity
    }

    @Provides
    @PerActivity
    fun provideContext(): WeakReference<Context?> {
        return activity as WeakReference<Context?>
    }
}
