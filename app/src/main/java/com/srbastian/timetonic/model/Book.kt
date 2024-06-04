package com.srbastian.timetonic.model

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("ownerPrefs")
    val ownerPrefs: OwnerPreferences,
)
