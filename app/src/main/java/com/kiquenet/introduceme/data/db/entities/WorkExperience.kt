package com.kiquenet.introduceme.common.view_models.model

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
    indices = [Index(value = ["user_id", "id"], unique = true)]
)
data class WorkExperience(
    @PrimaryKey(autoGenerate = true) @NonNull var id: Long,
    @NonNull var user_id: Long,
    var name: String,
    var startDate: String? = null,
    var endDate: String? = null,
    var duties: String? = null
)