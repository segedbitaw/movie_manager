package com.example.moviemanager.ui.all_movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        //println("This is a message to the console 1111")
        _binding = Fragment1Binding.inflate(inflater, container, false)
        binding.AddBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragment1_to_movieForm)
            println("This is a message to the console 222222")
        }
        //println("This is a message to the console")
        return binding.root
        //println("This is a message to the console 99999")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movies?.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = ItemAdapterFragment1(it, object : ItemAdapterFragment1.ItemListener  {

                override fun onItemClicked(index: Int) {

                    Toast.makeText(requireContext(),
                        "${it[index]}", Toast.LENGTH_SHORT).show()
                }

                override fun onItemLongClicked(index: Int) {
                    viewModel.setMovie(it[index])
                    findNavController().navigate(R.id.action_fragment1_to_movieDetails2)
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
                viewModel.deleteMovie(movie)

            }

        }
        ).attachToRecyclerView(binding.recyclerView)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
