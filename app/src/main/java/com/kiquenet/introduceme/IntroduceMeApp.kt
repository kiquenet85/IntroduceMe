package com.kiquenet.introduceme

import android.app.Application
import android.util.Log
import com.kiquenet.introduceme.di.component.ApplicationComponent
import com.kiquenet.introduceme.di.component.DaggerApplicationComponent
import com.kiquenet.introduceme.di.module.*
import javax.inject.Inject

/**
 * @author n.diazgranados
 */
class IntroduceMeApp @Inject constructor() : Application() {

    /**
     * To get ApplicationComponent
     * @return ApplicationComponent
     */
    val appComponent: ApplicationComponent
            by lazy {
                DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .networkModule(NetworkModule())
                    .respositoryModule(RespositoryModule())
                    .databaseModule(DatabaseModule())
                    .interactorModule(InteractorModule())
                    .build()
            }

    override fun onCreate() {
        super.onCreate()
        Log.d(this.javaClass.simpleName, "Initializing App")
        //dummy select statement for forcing DB creation.
        appComponent.getDB().query("select 1", null)
    }
}
