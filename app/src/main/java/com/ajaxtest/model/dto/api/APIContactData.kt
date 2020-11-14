package com.ajaxtest.model.dto.api

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class APIContactData(
    @SerializedName("name")
    val name: APIName? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("picture")
    val pic: APIPicture? = null
)

data class APIName(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("first")
    val first: String? = null,
    @SerializedName("last")
    val last: String? = null
)

data class APIPicture(
    @SerializedName("medium")
    val medium: String? = null
)

data class APIResWrap(
    @SerializedName("results")
    val contacts: MutableList<APIContactData>? = null
)