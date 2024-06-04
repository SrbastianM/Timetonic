package com.srbastian.timetonic.data.book

import com.google.gson.annotations.SerializedName
import com.srbastian.timetonic.model.Book
import com.srbastian.timetonic.model.Contact

data class AllBooks(
    val nbBooks: Int,
    val nbContacts: Int,
    val contacts: List<Contact>,
    @SerializedName("books")
    val books: List<Book>,
)
