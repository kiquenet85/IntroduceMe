package com.kiquenet.introduceme.common.view_models.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * @author n.diazgranados
 */
@Entity(
    tableName = "userExperience",
    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"])],
    indices = [Index(value = ["user_id", "id"], unique = true)],
    primaryKeys = ["user_id", "id"]
)
data class WorkExperience(
    @NonNull var id: Long,
    @NonNull var user_id: Long,
    var name: String,
    var startDate: String? = null,
    var endDate: String? = null,
    var duties: String? = null
)