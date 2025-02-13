package com.cineaste

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FavouriteMoviesFragment : Fragment() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private var favoriteMoviesList =  getFavoriteMovies()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_favourite_movies, container, false)
        favoriteMovies = view.findViewById(R.id.favoriteMovies)
        favoriteMovies.layoutManager = GridLayoutManager(activity, 2)
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()) { movie -> showMovieDetails(movie) }
        favoriteMovies.adapter=favoriteMoviesAdapter
        favoriteMoviesAdapter.updateMovies(favoriteMoviesList)
        return view;
    }
    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra("movie_title", movie.title)
        }
        startActivity(intent)
    }
}