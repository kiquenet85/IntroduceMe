package com.kiquenet.introduceme.di.module

import android.content.Context
import com.kiquenet.introduceme.data.UserRepository
import com.kiquenet.introduceme.di.scope.ApplicationContext
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoInteractor
import dagger.Module
import dagger.Provides

/**
 * @author n.diazgranados
 */
@Module
class InteractorModule {

    @Provides
    fun provideUserInfoInteractor(@ApplicationContext appContext: Context, userRepository: UserRepository) =
        UserInfoInteractor(appContext, userRepository)
}
