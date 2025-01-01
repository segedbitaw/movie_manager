package com.example.moviemanager.ui.all_movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemanager.R
import com.example.moviemanager.data.model.ItemManager
import com.example.moviemanager.databinding.Fragment1Binding
import com.example.moviemanager.ui.MovieFormViewModel
import com.example.moviemanager.ui.MovieViewModel

class Fragment1 : Fragment() {

    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieFormViewModel by activityViewModels()
    private val viewModel1: MovieViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        binding.AddBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragment1_to_movieForm)
        }

        binding.recyclerView.adapter = ItemAdapterFragment1(ItemManager.items)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())



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
                val position = viewHolder.adapterPosition
                ItemManager.remove(position)
                binding.recyclerView.adapter?.notifyItemRemoved(position)
            }

        }
        ).attachToRecyclerView(binding.recyclerView)
    }
    private fun setupObservers() {
        viewModel1.movies?.observe(viewLifecycleOwner){

        }

//        viewModel.title.observe(viewLifecycleOwner) { title ->
//            // You might want to update the list in Fragment1, not just the title in the text field
//        }
//        viewModel.description.observe(viewLifecycleOwner) { description ->
//            // Similarly, update the description or handle it as needed
//        }
//        viewModel.year.observe(viewLifecycleOwner) { year ->
//            // Similarly, update the description or handle it as needed
//        }
//        viewModel.posterUri.observe(viewLifecycleOwner) { uri ->
//            uri?.let {
//                Glide.with(requireContext())
//                    .load(it)
//                    .into(binding.resultImage)
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
