package com.kiquenet.introduceme.ui.di.component

import android.content.Context
import com.google.gson.Gson
import com.kiquenet.introduceme.ui.IntroduceMeApp
import com.kiquenet.introduceme.ui.activity.BaseActivity
import com.kiquenet.introduceme.ui.di.module.ApplicationModule
import com.kiquenet.introduceme.ui.di.module.NetworkModule
import com.kiquenet.introduceme.ui.di.module.RespositoryModule
import com.kiquenet.introduceme.ui.di.scope.ApplicationContext
import com.kiquenet.introduceme.ui.network.NetworkManager
import com.kiquenet.introduceme.ui.network.WifiReceiver
import com.kiquenet.introduceme.ui.settings.Settings
import dagger.Component

import javax.inject.Singleton

/**
 * @author n.diazgranados
 */
@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, RespositoryModule::class])
interface ApplicationComponent {

    //Network component
    val networkManager: NetworkManager
    val defaultGson: Gson

    //App component
    val application: IntroduceMeApp
    @get:ApplicationContext
    val appContext: Context

    //Presenter Component


    //Interactor


    //Others
    val settings: Settings

    //App
    fun inject(application: IntroduceMeApp)

    //Views injection
    fun inject(baseActivity: BaseActivity)

    //void inject(BaseFragment baseFragment);
    fun inject(wifiReceiver: WifiReceiver)
    /*PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    CompositeSubscription compositeSubscription();*/
}
