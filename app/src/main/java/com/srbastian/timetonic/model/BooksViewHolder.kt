package com.srbastian.timetonic.model

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srbastian.timetonic.databinding.ItemBookBinding

class BooksViewHolder(private val binding: ItemBookBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val name = binding.bookName
    val image = binding.bookImage

    fun bind(bookModel: BookDisplay) {
        name.text = bookModel.title
        Glide.with(binding.root.context).load(bookModel.coverImg).into(binding.bookImage)
    }
}
