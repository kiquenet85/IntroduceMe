package com.kiquenet.introduceme.data.remote.apis

import com.kiquenet.introduceme.feature.profile.UserInformationUseCase.UserInfoUi
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author n.diazgrandos
 */
interface IntroduceMeApi {

    @GET("introduceme/users/{userid}")
    suspend fun getUser(@Path("userid") userId: Long): Deferred<UserInfoUi>
}