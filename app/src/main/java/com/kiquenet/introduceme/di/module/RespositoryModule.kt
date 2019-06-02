package com.kiquenet.introduceme.di.module

import com.kiquenet.introduceme.data.UserRepository
import com.kiquenet.introduceme.data.db.AppDatabase
import com.kiquenet.introduceme.network.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author n.diazgranados
 */
@Module
class RespositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(appDatabase: AppDatabase, networkManager: NetworkManager)
            = UserRepository.getInstance(networkManager, appDatabase)
}
