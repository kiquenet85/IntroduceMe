package com.kiquenet.introduceme.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.kiquenet.introduceme.data.db.dao.UserDao
import com.kiquenet.introduceme.util.DATABASE_NAME
import com.kiquenet.introduceme.view_models.model.*

/**
 * The Room database for this app
 * @author n.diazgranados
 */
@Database(
    entities = [User::class, Course::class, Name::class, Academium::class, WorkExperience::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        private val TAG = AppDatabase::class.java.simpleName
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(appContext: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(appContext).also { instance = it }
            }
        }

        private fun buildDatabase(appContext: Context): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        Log.v(TAG, "App databse callback after start $DATABASE_NAME")
                        super.onCreate(db)

                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                            .addTag("MOCK DB DATA")
                            .build()
                        WorkManager.getInstance(appContext).enqueue(request)
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
