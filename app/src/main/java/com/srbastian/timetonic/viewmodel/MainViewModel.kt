package com.srbastian.timetonic.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.srbastian.timetonic.adapter.BooksAdapter
import com.srbastian.timetonic.model.BookDisplay
import com.srbastian.timetonic.network.RetrofitClient
import com.srbastian.timetonic.network.TimetonicInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _booksAdapter = MutableLiveData<BooksAdapter>()
    val booksAdapter: LiveData<BooksAdapter> get() = _booksAdapter
    private val api: TimetonicInterface = RetrofitClient.service

    init {
        _booksAdapter.value = BooksAdapter(emptyList())
    }

    fun fetchBooks(
        u_c: String,
        o_u: String,
        sessKey: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    api.getAllBooks(
                        sessKey = sessKey,
                        o_u = o_u,
                        u_c = u_c,
                    )
                Log.d("TAG", "Get All Books: $response")
                if (response.isSuccessful) { // here
                    val bookList = response.body()?.allBooks?.books ?: emptyList()
                    Log.d("TAG", "bookList: $bookList")

                    val bookDisplays =
                        bookList.map { book ->
                            val coverImageUrl =
                                book.ownerPrefs.coverImg
                            val title =
                                book.ownerPrefs.title
                            val coverImageReplace = coverImageUrl.replace("/dev/", "/")
                            BookDisplay(
                                title,
                                "https://timetonic.com/live$coverImageReplace",
                            )
                        }
                    Log.d("TAGd", "bookDisplays: $bookDisplays")
                    withContext(Dispatchers.Main) {
                        _booksAdapter.value = BooksAdapter(bookDisplays)
                    }
                } else {
                    handleError("Failed To Fetch Books")
                }
            } catch (e: Exception) {
                handleError("Unkown error: ${e.message}")
            }
        }
    }

    private suspend fun handleError(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(getApplication(), "Error: $message", Toast.LENGTH_SHORT).show()
        }
    }
}
