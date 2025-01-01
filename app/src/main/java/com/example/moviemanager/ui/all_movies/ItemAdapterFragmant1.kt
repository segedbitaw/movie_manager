package com.example.moviemanager.ui.all_movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviemanager.data.model.Movie
import com.example.moviemanager.databinding.CardForFirstFragmentBinding

class ItemAdapterFragment1(private val items: List<Movie>) :
    RecyclerView.Adapter<ItemAdapterFragment1.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: CardForFirstFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.itemTitle.text = item.title
            binding.itemDescription.text = item.description
            binding.itemYear.text = item.year
            Glide.with(binding.root).load(item.photo).circleCrop().into(binding.itemImage)
        }
    }

    fun itemAt(position:Int) = items[position]

    // Inflate layout and create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ItemViewHolder(CardForFirstFragmentBinding.inflate(LayoutInflater.from(parent.context)))

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)=
        holder.bind(items[position])

    // Return the total number of items
    override fun getItemCount(): Int {
        return items.size
    }
}
