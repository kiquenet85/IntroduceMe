package com.kiquenet.introduceme

import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.kiquenet.introduceme.feature.profile.UserInformationUseCase.UserInfoUi
import com.kiquenet.introduceme.feature.profile.UserInformationUseCase.UserInfoUiDeserializer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    companion object {
        const val MOCK_USERS_JSON = "users_mock.json"
    }

    lateinit var gson : Gson

    @Before
    @Throws(IOException::class)
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation()
            .getTargetContext().getApplicationContext()

        val userIInfoUiDeserializer = UserInfoUiDeserializer(appContext)
        gson =  GsonBuilder().registerTypeAdapter(UserInfoUi::class.java, UserInfoUiDeserializer(appContext)).create()
        userIInfoUiDeserializer.gson  = gson
    }

    @Test
    fun readJsonMockFiles() {
        val appContext = InstrumentationRegistry.getInstrumentation()
            .getTargetContext().getApplicationContext()
        val assets = appContext.getAssets()

        appContext.assets.open(MOCK_USERS_JSON).use { inputStream ->
            val content = inputStream.bufferedReader().use(BufferedReader::readText)
            val userType = object : TypeToken<List<UserInfoUi>>() {}.type
            val listUsers: List<UserInfoUi> = gson.fromJson(content, userType)

            assert(listUsers != null)
            assertEquals(10, listUsers.size)
        }
    }
}


fun readJsonFile(filename: String): String {
    val br = BufferedReader(InputStreamReader(FileInputStream(filename)))
    val sb = StringBuilder()
    var line = br.readLine()
    while (line != null) {
        sb.append(line)
        line = br.readLine()
    }

    return sb.toString()
}