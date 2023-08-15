package com.islam.tasks.movies.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.islam.tasks.core.extensions.gone
import com.islam.tasks.core.extensions.visible
import com.islam.tasks.movies.databinding.FragmentMoviesBinding
import com.islam.tasks.movies.presentation.adapter.MoviesAdapter
import com.islam.tasks.movies.presentation.navigation.MoviesNavigation
import com.islam.tasks.movies.presentation.stateholder.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    @Inject
    lateinit var moviesNavigation: MoviesNavigation
    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter {
            val request =
                NavDeepLinkRequest.Builder.fromUri(moviesNavigation.getDetailsDeepLink(it.id).toUri())
                    .build()
            findNavController().navigate(request)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeOnUiUpdates()
        viewModel.getMovies()
    }

    private fun initViews() = with(binding) {
        rvMovies.adapter = adapter
    }

    private fun observeOnUiUpdates() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesStateFlow.collectLatest { movies ->
                    adapter.submitList(movies)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorResChannel.collectLatest {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorMsgChannel.collectLatest {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingChannel.collectLatest { isLoading ->
                    with(binding.pbLoading) {
                        if (isLoading) visible()
                        else gone()
                    }
                }
            }
        }
    }

}