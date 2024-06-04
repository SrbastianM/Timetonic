package com.srbastian.timetonic.data.book

import com.google.gson.annotations.SerializedName

data class BooksResponse(
    val status: String,
    val sstamp: Long,
    @SerializedName("allBooks")
    val allBooks: AllBooks,
)
