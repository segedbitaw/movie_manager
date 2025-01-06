package com.example.moviemanager.ui.add_movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviemanager.R
import com.example.moviemanager.data.model.Movie
import com.example.moviemanager.databinding.FragmentMovieFormBinding
import com.example.moviemanager.ui.MovieViewModel
class MovieForm : Fragment() {
    private var _binding :FragmentMovieFormBinding?=null
    private val binding get()  =_binding!!
    private val viewModel1: MovieViewModel by activityViewModels()
    private var imageUri: Uri? = null

    val pickImageLauncher : ActivityResultLauncher<Array<String>> = registerForActivityResult(
        ActivityResultContracts.OpenDocument()){
        binding.resultImage.setImageURI(it)
        requireActivity().contentResolver.takePersistableUriPermission(it!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        imageUri = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentMovieFormBinding.inflate(inflater,container,false)
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel1.chosenMovie.observe(viewLifecycleOwner) {
            binding.MovieTitle.setText(it.title)
            binding.MovieDescription.setText(it.description)
            binding.MovieYear.setText(it.year)
            Glide.with(requireContext()).load(it.photo).circleCrop()
                .into(binding.resultImage)
            viewModel1.updateMovie(it)
        }

    }
    private fun setupListeners() {
        binding.finishEdit.setOnClickListener {
            val movieTitle = binding.MovieTitle.text.toString()
            val movieDescription = binding.MovieDescription.text.toString()
            if (movieTitle.isNotEmpty() && movieDescription.isNotEmpty()) {
                // Update ViewModel
                // Create movie and save to list
                val movie = Movie(
                    photo = imageUri.toString(),
                    title = movieTitle,
                    description = movieDescription,
                    year = binding.MovieYear.text.toString()
                )
                // Update ViewModel
                findNavController().navigate(R.id.action_movieForm_to_fragment12)
            }

            else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imageBtn.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }
        binding.editButton.setOnClickListener {
            val movie = viewModel1.chosenMovie.value // Access the currently selected movie
            if (movie != null) {
                // Create a new Movie object with updated values
                val updatedMovie = movie.copy(
                    title = binding.MovieTitle.text.toString(),
                    description = binding.MovieDescription.text.toString(),
                    photo = imageUri?.toString() ?: movie.photo,
                    year = binding.MovieYear.text.toString()
                )
                viewModel1.updateMovie(updatedMovie) // Call updateMovie with the new object
                findNavController().navigate(R.id.action_movieForm_to_fragment12)
            } else {
                Toast.makeText(context, "No movie selected for editing", Toast.LENGTH_SHORT).show()
            }
        }

    }
    ////////////////////////////// here edit button

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}