package com.example.moviemanager.ui.all_movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviemanager.data.model.Movie
import com.example.moviemanager.databinding.CardForFirstFragmentBinding

class ItemAdapterFragment1( val items: List<Movie>, val callBack: ItemListener)
    : RecyclerView.Adapter<ItemAdapterFragment1.ItemViewHolder>() {

    interface ItemListener {
        fun onItemClicked(index:Int)
        fun onItemLongClicked(index: Int)
    }

    inner class ItemViewHolder(private val binding: CardForFirstFragmentBinding) :
        RecyclerView.ViewHolder(binding.root),View.OnClickListener,View.OnLongClickListener {
        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            callBack.onItemClicked(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            callBack.onItemLongClicked(adapterPosition)
            return false
        }

        fun bind(item: Movie) {
            binding.itemTitle.text = item.title
//            binding.itemDescription.text = item.description
            binding.itemYear.text = item.year
            Glide.with(binding.root).load(item.photo).circleCrop().into(binding.itemImage)
        }
    }

    fun itemAt(position:Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ItemViewHolder(CardForFirstFragmentBinding.inflate(LayoutInflater.from(parent.context)))

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])

    // Return the total number of items
    override fun getItemCount() = items.size

}
