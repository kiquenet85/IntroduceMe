package com.kiquenet.introduceme.ui.di.component

import android.app.Activity
import android.content.Context
import com.kiquenet.introduceme.ui.activity.BaseActivity
import com.kiquenet.introduceme.ui.di.module.Activity.ActivityModule
import com.kiquenet.introduceme.ui.di.scope.PerActivity
import dagger.Component

/**
 * This component inject dependencies to all Activities across the application
 * @author n.diazgranados
 */

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    //Activity Component
    @get:PerActivity
    val context: Context

    @get:PerActivity
    val activity: Activity

    //Views injection
    fun inject(baseActivity: BaseActivity)
}
