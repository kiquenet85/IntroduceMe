package com.kiquenet.introduceme.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kiquenet.introduceme.common.view_models.model.*

/**
 * The Data Access Object for the Plant class.
 * @author n.diazgranados
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY first")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id = :id ORDER BY first")
    fun getUser(id: Long): LiveData<User>

    @Query("SELECT * FROM users WHERE id = :id ORDER BY first")
    fun getUserSync(id: Long): User

    @Query("SELECT * FROM users JOIN userCourses ON users.id = userCourses.user_id WHERE users.id = :id ORDER BY first, name")
    fun getUserCourses(id: Long): LiveData<List<Course>>

    @Query("SELECT * FROM users JOIN usersEducation ON users.id = usersEducation.user_id WHERE users.id = :id ORDER BY first, name")
    fun getUserEducation(id: Long): LiveData<List<Academium>>

    @Query("SELECT * FROM users JOIN userExperience ON users.id = userExperience.user_id WHERE users.id = :id ORDER BY first, name")
    fun getUserWorkExperience(id: Long): LiveData<List<WorkExperience>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNames(name: List<Name>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(users: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllUserCourses(users: List<Course>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllUserEducation(users: List<Academium>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllUserWorkExperience(users: List<WorkExperience>)

    @Update
    suspend fun updateUser(users: User)
}
