package com.islam.tasks.details.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.islam.tasks.core.extensions.gone
import com.islam.tasks.core.extensions.visible
import com.islam.tasks.details.databinding.FragmentMovieDetailsBinding
import com.islam.tasks.details.presentation.model.MovieDetailsIntent
import com.islam.tasks.details.presentation.model.MovieDetailsState
import com.islam.tasks.details.presentation.stateholder.MovieDetailsViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOnUiUpdates()
        viewModel.sendIntent(MovieDetailsIntent.GetDetails(args.id))
    }

    private fun observeOnUiUpdates() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                state.collectLatest(::updateUI)
            }
        }
    }

    private fun updateUI(state: MovieDetailsState) = with(state) {
        with(binding.pbLoading) {
            if (isLoading) visible()
            else gone()
        }
        movieDetails?.let {
            with(binding) {
                Picasso.get().load(movieDetails.posterPath).into(ivPoster)
                tvTitle.text = it.title
                tvReleaseDate.text = it.releaseDate
                tvOverView.text = it.overview
            }
        }
        errorRes?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        errorMsg?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }
}