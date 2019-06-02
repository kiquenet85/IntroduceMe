package com.kiquenet.introduceme.view_models.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author n.diazgranados
 */
@Entity(
    tableName = "usersEducation",
    foreignKeys = [androidx.room.ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    )],
    indices = [Index("user_id")]
)
data class Academium(
    @PrimaryKey @NonNull  var user_id: Long,
    var name: String,
    var degree: String? = null
)