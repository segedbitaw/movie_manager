package com.example.moviemanager.ui.all_movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemanager.R
import com.example.moviemanager.data.model.Movie
import com.example.moviemanager.databinding.Fragment1Binding
import com.example.moviemanager.ui.MovieViewModel

@Suppress("DEPRECATION")
class Fragment1 : Fragment() {

    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
               setHasOptionsMenu(true)
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

                override fun onItemLongClicked(index:Int) {
                    viewModel.setMovie(it[index])
                    findNavController().navigate(R.id.action_fragment1_to_movieForm)
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
                // Show delete confirmation dialog
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Delete Movie")
                builder.setMessage("Are you sure you want to delete this movie?")

                // Positive button - Delete movie
                builder.setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteMovie(movie)
                    Toast.makeText(context, "Movie deleted", Toast.LENGTH_SHORT).show()
                }

                // Negative button - Cancel (undo swipe action)
                builder.setNegativeButton("No") { dialog, _ ->
                    // Revert the swipe action by notifying the adapter
                    (binding.recyclerView.adapter as ItemAdapterFragment1).notifyItemChanged(viewHolder.adapterPosition)
                    dialog.dismiss()  // Dismiss the dialog
                }
                builder.show()  // Show the dialog
            }

        }
        ).attachToRecyclerView(binding.recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_delete){
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete all the items?")
                .setPositiveButton("Yes") { p0, p1 ->
                    viewModel.deleteAll()
                    Toast.makeText(requireContext(),"Items Deleted", Toast.LENGTH_SHORT).show()
                }.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
