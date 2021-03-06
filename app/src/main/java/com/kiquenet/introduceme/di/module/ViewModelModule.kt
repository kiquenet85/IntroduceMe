package com.kiquenet.introduceme.di.module

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kiquenet.introduceme.di.scope.ApplicationContext
import com.kiquenet.introduceme.feature.profile.UserInformationUseCase.UserInfoInteractor
import com.kiquenet.introduceme.feature.profile.view_model.UserInfoViewModel
import com.kiquenet.introduceme.common.view_models.factory.ViewModelFactory
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * @author n.diazgranados
 * Module using [ViewModelFactory] to be able to inject other objects in View models.
 *
 *  //https://www.techyourchance.com/dependency-injection-viewmodel-with-dagger-2/
 */ //https://azabost.com/injectable-android-viewmodels/
@Module
class ViewModelModule {

    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    fun provideViewModelFactory(providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory(providerMap)
    }

    //Place here all modules needed to be injected by dagger.
    @Provides
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    fun provideUserInfoViewModel(@ApplicationContext appContext: Context, userInfoInteractor: UserInfoInteractor): ViewModel {
        return UserInfoViewModel(appContext, userInfoInteractor)
    }
}