package com.ajaxtest.model.dto.processed

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ContactData")
data class ContactData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @Embedded
    val name: Name,
    val email: String,
    @Embedded
    val pic: Picture
) : Parcelable

@Parcelize
data class Name(
    val title: String,
    val first: String,
    val last: String
) : Parcelable

@Parcelize
data class Picture(
    val medium: String?
) : Parcelable