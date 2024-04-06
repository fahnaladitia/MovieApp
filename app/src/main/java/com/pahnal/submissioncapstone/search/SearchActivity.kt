package com.pahnal.submissioncapstone.search

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pahnal.submissioncapstone.core.ui.MoviePagingAdapter
import com.pahnal.submissioncapstone.databinding.ActivitySearchBinding
import com.pahnal.submissioncapstone.movie_detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import com.pahnal.submissioncapstone.R as resourceApp
import com.pahnal.submissioncapstone.core.R as resourceCore

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private lateinit var adapter: MoviePagingAdapter
    private lateinit var rvMovie: RecyclerView

    private val viewModel: SearchViewModel by viewModels()

    private var currentQuery: String = "a"

    private val movieDetailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.searchMovies(currentQuery)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.searchBar)
        supportActionBar?.title = getString(resourceApp.string.search)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAdapters()
        setupObservers()
        setupButton()
    }

    private fun setupButton() {
        binding.srl.setOnRefreshListener {
            viewModel.searchMovies(query = currentQuery)
        }

    }


    private fun setupAdapters() {
        rvMovie = binding.rvMovie
        adapter = MoviePagingAdapter(
            onClick = { movie ->
                val intent = Intent(this, MovieDetailActivity::class.java)
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
                movieDetailLauncher.launch(intent)
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

    private fun setupObservers() {
        viewModel.currentQuery.observe(this) { value ->
            currentQuery = value
        }
        viewModel.isLoading.observe(this) { value ->
            binding.srl.isRefreshing = value
        }
        viewModel.moviePagingList.observe(this) { value ->
            adapter.submitData(lifecycle, value)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val menuItem = menu?.findItem(resourceApp.id.app_bar_search)
        val ediExt: EditText? =
            (menuItem?.actionView as? SearchView)?.findViewById(androidx.appcompat.R.id.search_src_text)
        ediExt?.setTextColor(resources.getColor(resourceCore.color.white, null))
        ediExt?.setHintTextColor(resources.getColor(resourceCore.color.white, null))
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(resourceApp.menu.search_menu, menu)
        val menuItem = menu?.findItem(resourceApp.id.app_bar_search)
        setupSearchView((menuItem?.actionView as? SearchView))
        return true
    }

    private fun setupSearchView(searchView: SearchView?) {
        searchView?.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                Log.d(TAG, "onQueryTextChange: $newText")
                val text = newText.ifEmpty { "a" }
                viewModel.searchMovies(query = text)

                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                overrideActivityTransition(
                    OVERRIDE_TRANSITION_CLOSE,
                    androidx.appcompat.R.anim.abc_fade_in,
                    androidx.appcompat.R.anim.abc_fade_out
                )
            } else {
                @Suppress("DEPRECATION")
                overridePendingTransition(
                    androidx.appcompat.R.anim.abc_fade_in,
                    androidx.appcompat.R.anim.abc_fade_out
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = SearchActivity::class.java.simpleName
    }
}