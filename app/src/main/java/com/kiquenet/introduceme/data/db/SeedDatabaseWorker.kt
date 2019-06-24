package com.kiquenet.introduceme.data.db

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.reflect.TypeToken
import com.kiquenet.introduceme.IntroduceMeApp
import com.kiquenet.introduceme.feature.profile.UserInformationUseCase.UserInfoUi
import com.kiquenet.introduceme.util.USER_DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.BufferedReader

/**
 * @author n.diazgranados
 * Seeding DB for Introduce me API
 */
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {
            try {
                applicationContext.assets.open(USER_DATA_FILENAME).use { inputStream ->
                    val content = inputStream.bufferedReader().use(BufferedReader::readText)
                    val gson = (applicationContext as IntroduceMeApp).appComponent.getDefaultGson()
                    val userType = object : TypeToken<List<UserInfoUi>>() {}.type
                    val listUsers: List<UserInfoUi> = gson.fromJson(content, userType)

                    Log.v(TAG, "Database reader mock file users list successfully")

                    //Save in DB
                    val userInfoInteractor = (applicationContext as IntroduceMeApp).appComponent.getUserInfoInteractor()
                    val database = (applicationContext as IntroduceMeApp).appComponent.getDB()

                    userInfoInteractor.insertListUserInfosUi(listUsers)

                    Log.v(TAG, "Database was loaded with User MOCKS")
                    Result.success()
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error seeding database", ex)
                Result.failure()
            }
        }
    }
}

