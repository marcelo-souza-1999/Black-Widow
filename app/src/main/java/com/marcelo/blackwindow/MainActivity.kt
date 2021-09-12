package com.marcelo.blackwindow

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcelo.blackwindow.data.repository.MoviesApiDataSource
import com.marcelo.blackwindow.databinding.ActivityMainBinding
import com.marcelo.blackwindow.ui.adapter.MoviesAdapter
import com.marcelo.blackwindow.ui.model.MoviesViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MoviesViewModel by viewModels{
            MoviesViewModel.ViewModelFactory(MoviesApiDataSource())
        }

        setupToolbar()
        changedIconMoviesFavorite()
        settingsFlipper(viewModel)
        loadingDataMovies(viewModel)
    }

    private fun setupToolbar()
    {
        binding.toolBarMovie.setTitle("")
        binding.toolBarMovie.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(binding.toolBarMovie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun changedIconMoviesFavorite()
    {
        binding.toggleButtonListMovieCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                binding.toggleButtonListMovieCheck.setButtonDrawable(R.drawable.ic_favorite_movie)
            }
            else
            {
                binding.toggleButtonListMovieCheck.setButtonDrawable(R.drawable.ic_favorite_border_movie)
            }
        }
    }

    private fun settingsFlipper(viewModel: MoviesViewModel)
    {
        viewModel.viewFlipperMovieLiveData.observe(this)
        {
            it?.let { viewFlipper ->
                binding.viewFlipperMovie.displayedChild = viewFlipper.first

                viewFlipper.second?.let {
                    binding.textViewError.text = getString(it)
                }
            }
        }

        viewModel.viewFlipperSimilarMoviesLiveData.observe(this)
        {
            it?.let { viewFlipper ->
                binding.viewFlipperSimilarMovies.displayedChild = viewFlipper.first

                viewFlipper.second?.let {
                    binding.textViewErrorSimilar.text = getString(it)
                }
            }
        }
    }

    private fun loadingDataMovies(viewModel: MoviesViewModel)
    {
        viewModel.similarMoviesLiveData.observe(this) {
            it?.let { similarMovies ->
                with(binding.recyclerSimilarMovies)
                {
                    layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                    adapter = MoviesAdapter(similarMovies, viewModel.generyLiveData.value)
                }
            }
        }
        viewModel.movieLiveData.observe(this)
        {
            it?.let { movie ->
                binding.textViewMovieTitle.text = movie.title
                binding.textViewLikes.text = "${movie.vote_count} " + getString(R.string.likes)
                binding.textViewPopularity.text = "${movie.popularity} " + getString(R.string.views)
                if (movie.poster_path != null)
                {
                    Picasso.get().load("http://image.tmdb.org/t/p/w500${movie.poster_path}")
                        .into(binding.imageViewMovie)
                }
                else
                {
                    binding.imageViewMovie.setImageResource(R.drawable.ic_baseline_broken)
                }

            }
        }
        viewModel.getMovieDetails()
        viewModel.getSimilarMovies()
        viewModel.getGenre()
    }
}