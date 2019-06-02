package com.kiquenet.introduceme.di.component

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.kiquenet.introduceme.IntroduceMeApp
import com.kiquenet.introduceme.data.UserRepository
import com.kiquenet.introduceme.data.db.AppDatabase
import com.kiquenet.introduceme.di.module.*
import com.kiquenet.introduceme.di.scope.ApplicationContext
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoInteractor
import com.kiquenet.introduceme.network.NetworkManager
import com.kiquenet.introduceme.network.WifiReceiver
import com.kiquenet.introduceme.settings.Settings
import com.kiquenet.introduceme.ui.activity.BaseActivity
import com.kiquenet.introduceme.ui.fragments.BaseFragment
import com.kiquenet.introduceme.ui.fragments.ProfileFragment
import com.kiquenet.introduceme.view_models.UserInfoViewModel
import com.kiquenet.introduceme.view_models.factory.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

/**
 * @author n.diazgranados
 */
@Singleton
@Component(
    modules = [ApplicationModule::class,
        NetworkModule::class,
        RespositoryModule::class,
        InteractorModule::class,
        DatabaseModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    //Network component
    fun getNetworkManager(): NetworkManager

    fun getDefaultGson(): Gson

    //App component
    @ApplicationContext
    fun getApplication(): Application

    @ApplicationContext
    fun getAppContext(): Context

    //Repository
    fun getUserRepository(): UserRepository

    //Interactor
    fun getUserInfoInteractor(): UserInfoInteractor

    //ViewModel
    fun getViewModelFactory(): ViewModelFactory
    fun getUserInfoViewModel(): UserInfoViewModel

    //DB
    fun getDB(): AppDatabase

    //Others
    fun geSettings(): Settings

    //App
    fun inject(application: IntroduceMeApp)

    //Views injection
    fun inject(baseActivity: BaseActivity)

    fun inject(baseFragment: BaseFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(wifiReceiver: WifiReceiver)
}
