package com.pahnal.submissioncapstone.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pahnal.submissioncapstone.R
import com.pahnal.submissioncapstone.core.domain.enums.MovieType
import com.pahnal.submissioncapstone.core.ui.MoviePagingAdapter
import com.pahnal.submissioncapstone.core.utils.toGone
import com.pahnal.submissioncapstone.core.utils.toVisible
import com.pahnal.submissioncapstone.databinding.ActivityMainBinding
import com.pahnal.submissioncapstone.movie_detail.MovieDetailActivity
import com.pahnal.submissioncapstone.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieTypeMenu: PopupMenu

    private lateinit var adapter: MoviePagingAdapter
    private lateinit var rvMovie: RecyclerView
    private var currentMovieType: MovieType = MovieType.POPULAR

    private val viewModel: MainViewModel by viewModels()

    private val requestPageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.getMovies(currentMovieType)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.searchBar)
        supportActionBar?.title = getString(R.string.app_name)

        setupMenuType()
        setupAdapters()
        setupObservers()
        setupButton()
    }

    private fun setupButton() {
        binding.srl.setOnRefreshListener {
            viewModel.getMovies(currentMovieType)
        }
        binding.movieMenuType.setOnClickListener {
            movieTypeMenu.show()
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(this) { value ->
            binding.srl.isRefreshing = value
            if (!value) {
                binding.linearType.toVisible()
            }
        }
        viewModel.moviePagingList.observe(this) { value ->
            adapter.submitData(lifecycle, value)
        }
        viewModel.movieType.observe(this) { value ->
            currentMovieType = value
            binding.tvMenuType.text = when (value) {
                MovieType.POPULAR -> getString(R.string.popular)
                MovieType.NOW_PLAYING -> getString(R.string.now_playing)
                MovieType.UPCOMING -> getString(R.string.upcoming)
                MovieType.TOP_RATED -> getString(R.string.top_rated)
                else -> getString(R.string.popular)
            }
        }
    }

    private fun setupAdapters() {
        rvMovie = binding.rvMovie
        adapter = MoviePagingAdapter(
            onClick = { movie ->
                val intent = Intent(this,MovieDetailActivity::class.java)
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
                requestPageLauncher.launch(intent)

            },
            onClickButtonFavorite = { _, position ->
                adapter.snapshot()[position]?.let {
                    val isFavorite = !it.isFavorite
                    it.isFavorite = isFavorite
                    viewModel.setFavorite(it, isFavorite)
                    adapter.notifyItemChanged(position)
                }


            }
        )
        binding.rvMovie.layoutManager = GridLayoutManager(this, 3)
        binding.rvMovie.adapter = adapter
        binding.rvMovie.setHasFixedSize(true)
    }

    private fun setupMenuType() {
        binding.linearType.toGone()
        movieTypeMenu = PopupMenu(this, binding.movieMenuType)
        val menu = movieTypeMenu.menu
        movieTypeMenu.menuInflater.inflate(R.menu.movie_type_menu, menu)
        movieTypeMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.popular_type -> {
                    viewModel.getMovies(MovieType.POPULAR)
                    true
                }

                R.id.now_playing_type -> {
                    viewModel.getMovies(MovieType.NOW_PLAYING)
                    true
                }

                R.id.top_rated_type -> {
                    viewModel.getMovies(MovieType.TOP_RATED)
                    true
                }

                R.id.upcoming_type -> {
                    viewModel.getMovies(MovieType.UPCOMING)
                    true
                }

                else -> false
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_to_search -> {
                Log.d(TAG, "onOptionsItemSelected: action_to_search")
                val intent = Intent(this, SearchActivity::class.java)
                val options = ActivityOptionsCompat
                    .makeCustomAnimation(
                        this,
                        androidx.appcompat.R.anim.abc_fade_in,
                        androidx.appcompat.R.anim.abc_fade_out
                    )
                requestPageLauncher.launch(intent,options)
            }

            R.id.action_to_favorite -> {
                Log.d(TAG, "onOptionsItemSelected: action_to_favorite")
                val uri = Uri.parse("submissioncapstone://favorite")
                requestPageLauncher.launch(Intent(Intent.ACTION_VIEW, uri))
            }

        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}