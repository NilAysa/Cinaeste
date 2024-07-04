package com.cineaste

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movie: Movie
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var genre : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private val posterPath = "https://image.tmdb.org/t/p/w780"
    private val backdropPath = "https://image.tmdb.org/t/p/w500"
    private lateinit var backdrop : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)

        val extras = intent.extras
        if (extras != null) {
            movie = getMovieByTitle(extras.getString("movie_title",""))
            populateDetails()
        } else {
            finish()
        }

        website.setOnClickListener{
            showWebsite()
        }

        //Klik na naziv filma - pretraga tarilera na webu
        title.setOnClickListener {
            var pretraga=title.text.toString() +" trailer"
            searchWeb(pretraga)
        }

        val btn=findViewById<FloatingActionButton>(R.id.shareButton)
        btn.setOnClickListener {
            shareOverview()
        }
    }

    private fun shareOverview() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }

        try {
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }catch (e: ActivityNotFoundException) {
            e.printStackTrace();
        }
    }

    //pretraga sa defaultnom aplikacijom
    private fun searchWeb(pretraga: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_WEB_SEARCH
            putExtra(SearchManager.QUERY, pretraga)
        }
        try{
            startActivity(sendIntent)
        } catch (e: ActivityNotFoundException) {
           e.printStackTrace();
        }
    }

    private fun showWebsite() {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.homepage))
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }

    }
    private fun populateDetails() {
        title.text=movie.title
        releaseDate.text=movie.releaseDate
        website.text=movie.homepage
        overview.text=movie.overview
        val context: Context = poster.context
        var id: Int = context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        poster.setImageResource(id)
        Glide.with(context)
            .load(posterPath + movie.posterPath)
            .placeholder(R.drawable.picture1)
            .error(id)
            .fallback(id)
            .into(poster);
        var backdropContext: Context = backdrop.getContext()
        Glide.with(backdropContext)
            .load(backdropPath + movie.backdropPath)
            .centerCrop()
            .placeholder(R.drawable.backdrop)
            .error(R.drawable.backdrop)
            .fallback(R.drawable.backdrop)
            .into(backdrop);
    }
    private fun getMovieByTitle(name:String):Movie{
        val movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(getRecentMovies())
        movies.addAll(getFavoriteMovies())
        val movie= movies.find { movie -> name == movie.title }
        return movie?:Movie(0,"Test","Test","Test","Test","Test","Test")
    }
}