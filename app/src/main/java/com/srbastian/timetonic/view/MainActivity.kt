package com.srbastian.timetonic.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.srbastian.timetonic.adapter.BooksAdapter
import com.srbastian.timetonic.databinding.ActivityMainBinding
import com.srbastian.timetonic.model.Book
import com.srbastian.timetonic.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    lateinit var bookAdapter: BooksAdapter
    private val viewModel: MainViewModel by viewModels()

    var bookList = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        val sessKey =
            getSharedPreferences("Timetonic", Context.MODE_PRIVATE).getString("sess_key", null)
        val uc = "androiddeveloper"
        val ou = "androiddeveloper"
        if (sessKey.isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            viewModel.fetchBooks(u_c = uc, o_u = ou, sessKey = sessKey)
        }

        viewModel.booksAdapter.observe(
            this,
            Observer { adapter ->
                mainBinding.rvMainActivity.adapter = adapter
            },
        )

        mainBinding.rvMainActivity.layoutManager = LinearLayoutManager(this)
    }
}
