package com.kiquenet.introduceme.view_models.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@author n.diazgranados
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    val id: Long,
    var isActive: Boolean = false,
    var picture: String? = null,
    var age: Int,
    var company: String? = null,
    var email: String,
    var phone: String? = null,
    var address: String? = null,
    var about: String? = null,
    var registered: String,
    var latitude: String? = null,
    var longitude: String? = null,
    @Embedded var name: Name
)