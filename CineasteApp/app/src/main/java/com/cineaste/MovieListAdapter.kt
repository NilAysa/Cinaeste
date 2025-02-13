package com.cineaste

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class MovieListAdapter(
    private var movies: List<Movie>, private val onItemClicked: (movie:Movie) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private val posterPath = "https://image.tmdb.org/t/p/w342"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }
    override fun getItemCount(): Int = movies.size
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieTitle.text = movies[position].title;
        //val genreMatch: String? = movies[position].genre
        //Pronalazimo id drawable elementa na osnovu naziva zanra
        val context: Context = holder.movieImage.context
        var id: Int = context.getResources()
            .getIdentifier("picture1", "drawable", context.getPackageName())
        holder.movieImage.setImageResource(id)
        holder.itemView.setOnClickListener{ onItemClicked(movies[position]) }
        Glide.with(context)
            .load(posterPath + movies[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.picture1)
            .error(id)
            .fallback(id)
            .into(holder.movieImage);
        holder.itemView.setOnClickListener{ onItemClicked(movies[position]) }
    }
    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
    }
}
//spirala reftrofit koristit