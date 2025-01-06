package com.example.moviemanager.ui.all_movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemanager.R
import com.example.moviemanager.databinding.Fragment1Binding
import com.example.moviemanager.ui.MovieViewModel

class Fragment1 : Fragment() {

    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = Fragment1Binding.inflate(inflater, container, false)
        binding.AddBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragment1_to_movieForm)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movies?.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = ItemAdapterFragment1(it, object : ItemAdapterFragment1.ItemListener  {
                override fun onItemClicked(index: Int) {
                    viewModel.setMovie(it[index])
                    findNavController().navigate(R.id.action_fragment1_to_movieDetails2)
                }
                override fun onItemLongClicked(index: Int) {
                   viewModel.setMovie(it[index])
                    findNavController().navigate(R.id.action_fragment1_to_movieForm)
                    (binding.recyclerView.adapter as ItemAdapterFragment1).notifyItemChanged(index)
                }
            })
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        // ItemTouchHelper for swipe-to-remove functionality
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movie = (binding.recyclerView.adapter as ItemAdapterFragment1).itemAt(viewHolder.adapterPosition)
                // Show a confirmation dialog before deleting
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Delete Movie")
                    .setMessage("Are you sure you want to delete this movie?")
                    .setPositiveButton("Yes") { dialog, which ->
                        // User confirmed deletion, delete the movie
                        viewModel.deleteMovie(movie)  // Call ViewModel to delete movie from the database

                        // Notify the adapter that the item has been removed
                        (binding.recyclerView.adapter as ItemAdapterFragment1).notifyItemRemoved(viewHolder.adapterPosition)
                    }
                    .setNegativeButton("No") { dialog, which ->
                        // User canceled, so we restore the item back to its original position
                        (binding.recyclerView.adapter as ItemAdapterFragment1).notifyItemChanged(viewHolder.adapterPosition)
                    }
                // Show the dialog
                builder.create().show()
            }

        }
        ).attachToRecyclerView(binding.recyclerView)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
