package com.marcelo.blackwindow.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelo.blackwindow.R
import com.marcelo.blackwindow.data.model.Genery
import com.marcelo.blackwindow.data.model.SimilarMovies
import com.marcelo.blackwindow.databinding.MoviesListBinding
import com.squareup.picasso.Picasso

class MoviesAdapter(
    private val similarMovies: List<SimilarMovies>,
    private val generies: List<Genery>?) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>()
{
    private lateinit var binding: MoviesListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder
    {
        binding = MoviesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding, generies)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int)
    {
        holder.bind(similarMovies[position])
    }

    override fun getItemCount(): Int = similarMovies.size

    class MoviesViewHolder(binding: MoviesListBinding, private val genresList: List<Genery>?) : RecyclerView.ViewHolder(binding.root)
    {
        private val title = binding.txtNameMoviesList
        private val releaseDate = binding.txtYearMovieList
        private val posterPath = binding.imgMovieList
        private val genreMovie = binding.txtGenereMovieList
        private val toggleButton = binding.toggleBtnMovieListCheck

        fun bind(movie: SimilarMovies)
        {
            title.text = movie.title
            releaseDate.text = movie.release_date.substring(0, 4)
            Picasso.get().load("http://image.tmdb.org/t/p/w500${movie.poster_path}").into(posterPath)

            var genresCommons: List<Genery> = mutableListOf()
            if (genresList != null)
            {
                genresCommons = genresList.filter { movie.genre_ids.contains(it.id) }
            }

            val genresNames: MutableList<String> = mutableListOf()
            for (genre in genresCommons)
            {
                genresNames.add(genre.name)
            }
            genreMovie.text = genresNames.joinToString()

            toggleButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                {
                    toggleButton.setButtonDrawable(R.drawable.ic_check_circle_24)
                }
                else
                {
                    toggleButton.setButtonDrawable(R.drawable.ic_check_circle_outline_24)
                }
            }
        }
    }
}