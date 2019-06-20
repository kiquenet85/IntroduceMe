package com.kiquenet.introduceme.data

import android.util.Log
import com.kiquenet.introduceme.data.db.AppDatabase
import com.kiquenet.introduceme.data.remote.apis.IntroduceMeApi
import com.kiquenet.introduceme.feature.profile.UserInformationUseCase.UserInfoUi
import com.kiquenet.introduceme.network.NetworkManager
import com.kiquenet.introduceme.common.view_models.model.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Repository module for handling data operations.
 * @author n.diazgranados
 */
class UserRepository @Inject constructor(
    private val networkManager: NetworkManager,
    val appDatabase: AppDatabase
) {

    fun getUsers() = appDatabase.userDao().getUsers()

    fun getUser(id: Long) = appDatabase.userDao().getUser(id)

    fun getUserSync(id: Long) = appDatabase.userDao().getUserSync(id)

    fun getUserCourses(id: Long) = appDatabase.userDao().getUserCourses(id)

    fun getUserEducation(id: Long) = appDatabase.userDao().getUserEducation(id)

    fun getUserWorkExperience(id: Long) = appDatabase.userDao().getUserWorkExperience(id)

    //Remote
    suspend fun getRemoteUser(id: Long): Deferred<UserInfoUi> = coroutineScope {

        var deferred: Deferred<UserInfoUi> = CompletableDeferred(UserInfoUi())
        try {
            deferred = networkManager.defaultRetrofit.create(IntroduceMeApi::class.java).getUser(id)

            deferred.await()
        } catch (e: HttpException) {
            Log.e(IntroduceMeApi::class.java.simpleName, "Exception ${e.message}")
        } catch (e: Throwable) {
            Log.e(IntroduceMeApi::class.java.simpleName, "Exception ${e.message}")
        }
        deferred
    }

    //Insert
    fun insertUser(user: User) {
        user?.let {
            appDatabase.userDao().insertUser(user)
        }
    }

    //Update
    fun updateUser(user: User) {
        user?.let {
            appDatabase.userDao().updateUser(user)
        }
    }

    //Use cases
    //Interactor DB operations.
    fun insertListUserInfosUi(userList: List<UserInfoUi?>) {
        appDatabase.runInTransaction({
            userList.forEach {
                if (it != null) insertUserInfoUi(it)
            }
        })
    }

    //Insert list
    fun insertUserInfoUi(userInfoUi: UserInfoUi) {

        if (userInfoUi != null && userInfoUi.user != null) {

            val safeUser = userInfoUi.user!!

            with(userInfoUi) {
                user?.id?.let {
                    appDatabase.userDao().insertUser(safeUser)
                    courses?.let {
                        if (!it.isEmpty()) {
                            it.forEach { it.user_id = safeUser.id }
                            appDatabase.userDao().insertAllUserCourses(it)
                        }
                    }
                    academia?.let {
                        if (!it.isEmpty()) {
                            it.forEach { it.user_id = safeUser.id }
                            appDatabase.userDao().insertAllUserEducation(it)
                        }
                    }
                    workExperience?.let {
                        if (!it.isEmpty()) {
                            it.forEach { it.user_id = safeUser.id }
                            appDatabase.userDao().insertAllUserWorkExperience(it)
                        }
                    }
                }
            }
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(networkManager: NetworkManager, appDatabase: AppDatabase) =
            instance ?: synchronized(this) {
                instance
                    ?: UserRepository(
                        networkManager,
                        appDatabase
                    ).also { instance = it }
            }
    }
}