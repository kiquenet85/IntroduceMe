package com.kiquenet.introduceme.di.component

import android.app.Activity
import android.content.Context
import com.kiquenet.introduceme.di.module.Activity.ActivityModule
import com.kiquenet.introduceme.di.scope.PerActivity
import com.kiquenet.introduceme.ui.activity.BaseActivity
import dagger.Component
import java.lang.ref.WeakReference

/**
 * This component inject dependencies to all Activities across the application
 * @author n.diazgranados
 */

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    //Activity Component
    @PerActivity
    fun getContext(): WeakReference<Context?>

    @PerActivity
    fun getActivity(): WeakReference<Activity?>

    //Views injection
    fun inject(baseActivity: BaseActivity)
}
