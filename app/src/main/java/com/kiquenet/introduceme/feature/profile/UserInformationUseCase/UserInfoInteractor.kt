package com.kiquenet.introduceme.feature.profile.UserInformationUseCase

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kiquenet.introduceme.data.UserRepository
import com.kiquenet.introduceme.di.scope.ApplicationContext
import com.kiquenet.introduceme.util.combineAndCompute
import com.kiquenet.introduceme.util.getDistinct
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

/**
 * @author n.diazgranados
 * Defining interactors for specific use cases.
 * User information interactor brings all profile user information.
 */
class UserInfoInteractor @Inject constructor(
    @ApplicationContext val appContext: Context,
    val userRepository: UserRepository
) {

    companion object {
        private val TAG = UserInfoInteractor::class.java.name
    }

    val userInfo = MediatorLiveData<UserInfoUi>()

    /**
     * Getting the current database information.
     */
    suspend fun getUserInfo(id: Long) = withContext(Dispatchers.IO) {

        val userJob = async { userRepository.getUser(id).getDistinct() }
        val userCoursesJob = async { userRepository.getUserCourses(id).getDistinct() }
        val userWorkExperienceJob = async { userRepository.getUserWorkExperience(id).getDistinct() }
        val userEducationJob = async { userRepository.getUserEducation(id).getDistinct() }

        withContext(Dispatchers.Main) {
            val liveDataList = listOf<LiveData<*>>(
                userJob.await(), userWorkExperienceJob.await(),
                userCoursesJob.await(), userEducationJob.await()
            )
            try {
                userInfo.combineAndCompute(
                    liveDataList
                ) {
                    UserInfoUi(
                        userJob.getCompleted().value,
                        userEducationJob.getCompleted().value,
                        userWorkExperienceJob.getCompleted().value,
                        userCoursesJob.getCompleted().value
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Problem getting UserInfoUi information from DATABASE.")
                e.printStackTrace()
            }
        }
        launch { getRemoteUserInfo(id) }
    }

    suspend fun getRemoteUserInfo(userId: Long) = withContext(Dispatchers.IO) {
        try {
            val userRemoteJob = userRepository.getRemoteUser(userId)
            val remoteUser = userRemoteJob.await()

            userRepository.insertUserInfoUi(remoteUser)

        } catch (e: Exception) {
            Log.e(TAG, "Problem when calling userRemote from userRepository.")
            e.printStackTrace()
            simulateResponseFromWeb(userId)
        }
    }

    private suspend fun simulateResponseFromWeb(userId: Long) {
        delay(6000)
        val currentUser = userRepository.getUserSync(userId)
        currentUser?.let {
            currentUser.picture = "www.otherpicture.com/${Random().nextInt()}"
            userRepository.updateUser(currentUser)
        }
    }
}