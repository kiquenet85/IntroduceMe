package com.kiquenet.introduceme.view_models.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author n.diazgranados
 */
@Entity(
    tableName = "userExperience",
    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"])],
    indices = [Index("user_id")]
)
data class WorkExperience(
    @PrimaryKey @NonNull var user_id: Long,
    @NonNull val id: Long,
    var name: String,
    var startDate: String? = null,
    var endDate: String? = null,
    var duties: String? = null
)