package com.srbastian.timetonic.model

import com.google.gson.annotations.SerializedName

data class OwnerPreferences(
    @SerializedName("oCoverImg")
    val coverImg: String,
    @SerializedName("title")
    val title: String,
)
