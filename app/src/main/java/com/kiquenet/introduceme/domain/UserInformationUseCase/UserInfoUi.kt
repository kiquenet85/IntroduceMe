package com.kiquenet.introduceme.domain.UserInformationUseCase

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.kiquenet.introduceme.IntroduceMeApp
import com.kiquenet.introduceme.di.scope.ApplicationContext
import com.kiquenet.introduceme.view_models.model.*
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Data for UserInformation use case [UserInfoInteractor]
 */
data class UserInfoUi(
    var user: User? = null,
    var academia: List<Academium>? = null,
    var workExperience: List<WorkExperience>? = null,
    var courses: List<Course>? = null
)

class UserInfoUiDeserializer @Inject constructor(@ApplicationContext val appContext: Context) :
    JsonDeserializer<UserInfoUi?> {

    var gson: Gson? = null

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): UserInfoUi? {

        if (gson == null) gson = (appContext as IntroduceMeApp).appComponent.getDefaultGson()

        if (json != null && gson != null) {
            println("json: $json")
            val jsonObject = json.asJsonObject

            val name = Name(
                jsonObject.get("_id").asLong,
                jsonObject.getAsJsonObject("name").get("first").asString,
                jsonObject.getAsJsonObject("name").get("last").asString
            )

            val user = User(
                jsonObject.get("_id").asLong,
                jsonObject.get("isActive").asBoolean,
                jsonObject.get("picture").asString,
                jsonObject.get("age").asInt,
                jsonObject.get("company").asString,
                jsonObject.get("email").asString,
                jsonObject.get("phone").asString,
                jsonObject.get("address").asString,
                jsonObject.get("about").asString,
                jsonObject.get("registered").asString,
                jsonObject.get("latitude").asString,
                jsonObject.get("longitude").asString,
                name
            )

            val academiaType = object : TypeToken<List<Academium>>() {}.type
            val workExperienceType = object : TypeToken<List<WorkExperience>>() {}.type
            val courseType = object : TypeToken<List<Course>>() {}.type
            val academia: List<Academium> =
                gson!!.fromJson(jsonObject.get("academia").asJsonArray, academiaType)
            val workExperience: List<WorkExperience> =
                gson!!.fromJson(jsonObject.get("workExperience").asJsonArray, workExperienceType)
            val courses: List<Course> = gson!!.fromJson(jsonObject.get("courses").asJsonArray, courseType)
            return UserInfoUi(
                    user,
                    academia,
                    workExperience,
                    courses
                )
        }
        return null
    }
}

