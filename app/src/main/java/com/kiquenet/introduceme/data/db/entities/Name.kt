package com.kiquenet.introduceme.view_models.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author n.diazgranados
 */
@Entity(
    tableName = "userNames",
    foreignKeys = [androidx.room.ForeignKey(
        entity = com.kiquenet.introduceme.view_models.model.User::class,
        parentColumns = ["id"],
        childColumns = ["name_id"]
    )],
    indices = [Index("name_id")]
)
data class Name(
    @PrimaryKey @NonNull var name_id: Long,
    var first: String,
    var last: String
)
