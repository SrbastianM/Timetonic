package com.srbastian.timetonic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srbastian.timetonic.databinding.ItemBookBinding
import com.srbastian.timetonic.model.BookDisplay
import com.srbastian.timetonic.model.BooksViewHolder

class BooksAdapter(private val bookList: List<BookDisplay>?) :
    RecyclerView.Adapter<BooksViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BooksViewHolder {
        val view = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }

    override fun onBindViewHolder(
        holder: BooksViewHolder,
        position: Int,
    ) {
        bookList?.get(position)?.let { book ->
            holder.bind(book)
        }
    }
}
