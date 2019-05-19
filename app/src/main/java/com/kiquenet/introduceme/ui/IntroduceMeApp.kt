package com.kiquenet.introduceme.ui

import android.app.Application
import android.util.Log
import com.kiquenet.introduceme.ui.di.component.ApplicationComponent
import com.kiquenet.introduceme.ui.di.component.DaggerApplicationComponent
import com.kiquenet.introduceme.ui.di.module.ApplicationModule
import com.kiquenet.introduceme.ui.di.module.NetworkModule
import javax.inject.Inject

/**
 * @author n.diazgranados
 */
class IntroduceMeApp @Inject constructor() : Application() {

    private var applicationComponent: ApplicationComponent? = null

    /**
     * To get ApplicationComponent
     * @return ApplicationComponent
     */
    val appComponent: ApplicationComponent?
        get() {
            if (applicationComponent == null) {
                applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .networkModule(NetworkModule())
                    .build()
            }
            return applicationComponent
        }

    override fun onCreate() {
        super.onCreate()
        Log.d(this.javaClass.simpleName, "Initializing App")
    }

    /**
     * Need to replace the component with a test specific one
     * @param applicationComponent
     */
    fun setComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }

}
