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
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviemanager.R
import com.example.moviemanager.data.model.Movie
import com.example.moviemanager.databinding.FragmentMovieFormBinding
import com.example.moviemanager.ui.MovieViewModel

class MovieForm : Fragment() {
    private var _binding :FragmentMovieFormBinding?=null
    private val binding get()  =_binding!!

    //private val viewModel: MovieFormViewModel by activityViewModels()
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
        _binding = FragmentMovieFormBinding.inflate(inflater,container,false)
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel1.chosenMovie.observe(viewLifecycleOwner) {

            if (viewModel1.chosenMovie.value != null) {
                binding.MovieTitle.setText(it?.title)
                binding.MovieDescription.setText(it?.description)
                binding.MovieYear.setText(it?.year)
                binding.movieGenre?.setText(it?.genre)
                binding.ratingBar.rating = it?.stars ?: 0.0f // Provide default value if null
                Glide.with(requireContext()).load(it?.photo).circleCrop()
                    .into(binding.resultImage)
            }
        }

    }
    private fun setupListeners() {
        binding.finishEdit.setOnClickListener {
            val movieTitle = binding.MovieTitle.text.toString()
            val movieDescription = binding.MovieDescription.text.toString()
            if (movieTitle.isNotEmpty() && movieDescription.isNotEmpty()) {
                // Create movie and save to list
                val movie = Movie(
                    photo = imageUri.toString(),
                    title = movieTitle,
                    description = movieDescription,
                    year = binding.MovieYear.text.toString(),
                    genre = binding.movieGenre?.text.toString(),
                    stars = binding.ratingBar.rating
                )
                // Update ViewModel
                if(viewModel1.chosenMovie.value == null){
                    viewModel1.addMovie(movie)
                } else{
                    movie.id = viewModel1.chosenMovie.value?.id?: 0
                    viewModel1.updateMovie(movie)
                    viewModel1.clearChosenMovie()
                }
                findNavController().navigate(R.id.action_movieForm_to_fragment12)
            }
            else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
//            binding.MovieTitle.text?.clear()
//            binding.MovieDescription.text?.clear()
//            binding.MovieYear.text?.clear()
//            binding.resultImage.setImageDrawable(null)
        }
        binding.imageBtn.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}