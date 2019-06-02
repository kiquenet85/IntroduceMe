package com.kiquenet.introduceme.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kiquenet.introduceme.view_models.model.User
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 * @author n.diazgranados
 */
class Converters {

    companion object {
        val gson = Gson()
    }

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }


    @TypeConverter
    fun userToString(user: User): String = gson.toJson(user)

    @TypeConverter
    fun stringToUser(userString: String): User = gson.fromJson(userString, User::class.java)

    //LISTS
    @TypeConverter
    fun stringToList(data: String?): List<Any> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Any>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<Any>): String {
        return gson.toJson(someObjects)
    }
}