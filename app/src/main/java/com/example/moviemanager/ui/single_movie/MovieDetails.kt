package com.example.moviemanager.ui.single_movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.moviemanager.R
import com.example.moviemanager.databinding.FragmentMovieDetailsBinding
import com.example.moviemanager.ui.MovieViewModel


class MovieDetails : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.chosenMovie.observe(viewLifecycleOwner) {
            if (viewModel.chosenMovie.value != null) {
                binding.itemTitle.append(it?.title)
                binding.itemDesc.append(it?.description)
                binding.itemYear.append(it?.year)
                binding.itemGenre.append(it?.genre)
                binding.ratingBar.rating = it?.stars ?: 0.0f // Provide default value if null
                Glide.with(requireContext()).load(it?.photo).circleCrop()
                    .into(binding.itemImage)
                viewModel.clearChosenMovie()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}