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
        _binding=FragmentMovieFormBinding.inflate(inflater,container,false)
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

    }
    private fun setupListeners() {
        binding.finishEdit.setOnClickListener {
            //  update database, navigate back
            val movieTitle = binding.MovieTitle.text.toString()
            val movieDescription = binding.MovieDescription.text.toString()
            if (movieTitle.isNotEmpty() && movieDescription.isNotEmpty()) {
                // Update ViewModel
//                viewModel.setTitle(movieTitle)
//                viewModel.setDescription(movieDescription)
//                viewModel.setPosterUri(imageUri!!)
//                viewModel.setYear(binding.MovieYear.text.toString())

                // Create movie and save to list
                val movie = Movie(
                    photo = imageUri.toString(),
                    title = movieTitle,
                    description = movieDescription,
                    year = binding.MovieYear.text.toString()
                )
//                ItemManager.add(movie)
                viewModel1.addMovie(movie)

                findNavController().navigate(R.id.action_movieForm_to_fragment12, bundleOf("movie" to movie))
            }
            else {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
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