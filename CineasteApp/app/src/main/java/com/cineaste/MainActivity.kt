package com.cineaste

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    private val br: BroadcastReceiver = ConnectivityBroadcastReceiver()
    private val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navView.setupWithNavController(navController)
        if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
        {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                val bundle = bundleOf("search" to it)
                navView.selectedItemId= R.id.searchFragment
                navController.navigate(R.id.searchFragment,bundle)
            }
        }
        Intent(this, LatestMovieService::class.java).also {
            startForegroundService(it)
            return
        }
    }
    override fun onResume() {
        super.onResume()
        registerReceiver(br, filter)
    }

    override fun onPause() {
        unregisterReceiver(br)
        super.onPause()
    }
}

/* //lateinit definisimo i garantujemo da cemo inicijalizirati ovaj layout, zato sto sve inace ide poslije linije 24?
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private var favoriteMoviesList =  getFavoriteMovies()

    private lateinit var recentMovies: RecyclerView
    private lateinit var recentMoviesAdapter: MovieListAdapter
    private var recentMoviesList= getRecentMovies()

    private lateinit var searchText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        favoriteMovies = findViewById(R.id.favoriteMovies)
        favoriteMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        //favoriteMoviesAdapter = MovieListAdapter(listOf())
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()) { movie -> showMovieDetails(movie) }

        favoriteMovies.adapter = favoriteMoviesAdapter
        favoriteMoviesAdapter.updateMovies(favoriteMoviesList)

        recentMovies=findViewById(R.id.recentMovies)
        recentMovies.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        //recentMoviesAdapter= MovieListAdapter(listOf())
        recentMoviesAdapter = MovieListAdapter(arrayListOf()) { movie -> showMovieDetails(movie) }
        recentMovies.adapter=recentMoviesAdapter
        recentMoviesAdapter.updateMovies(recentMoviesList)

        searchText=findViewById(R.id.searchText)

        if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
            handleSendText(intent)
    }
    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie_title", movie.title)
        }
        startActivity(intent)
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            searchText.setText(it)
        }
    }*/