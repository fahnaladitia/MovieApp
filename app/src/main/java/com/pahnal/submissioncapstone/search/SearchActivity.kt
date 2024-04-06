package com.pahnal.submissioncapstone.search

import android.app.SearchManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pahnal.submissioncapstone.R
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

    private var currentQuery: String = ""

    private val movieDetailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.searchMovies(currentQuery)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate: $currentQuery")
            currentQuery = savedInstanceState.getString(CURRENT_SEARCH_QUERY) ?: ""
        }

        setSupportActionBar(binding.searchBar)
        supportActionBar?.title = getString(resourceApp.string.search)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAdapters()
        setupObservers()
        setupButton()
    }

    private fun setupButton() {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(CURRENT_SEARCH_QUERY, currentQuery)
        super.onSaveInstanceState(outState)
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

        adapter.addLoadStateListener { loadState ->
            when {
                loadState.refresh is LoadState.Error -> {
                    showError(loadState.refresh as? LoadState.Error)
                }

                loadState.append is LoadState.Error -> {
                    showError(loadState.append as? LoadState.Error)
                }

                loadState.prepend is LoadState.Error -> {
                    showError(loadState.prepend as? LoadState.Error)
                }
            }
            if (loadState.refresh is LoadState.NotLoading) {
                val isNotEmpty = adapter.itemCount > 1
                binding.rvMovie.isVisible = adapter.itemCount > 1
                binding.tvError.isVisible = !isNotEmpty
                binding.tvError.text = getString(R.string.empty)
            }
        }
    }

    private fun showError(error: LoadState.Error?) {
        error?.let {
            Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        viewModel.currentQuery.observe(this) { value ->
            binding.srl.setOnRefreshListener {
                viewModel.searchMovies(query = value)
            }
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

        val searchManager = getSystemService(SearchManager::class.java)
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.isIconified = true
        searchView?.onActionViewExpanded()
        searchView?.setQuery(currentQuery, false)
        searchView?.isFocusable = true



        searchView?.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                Log.d(TAG, "onQueryTextChange: $newText")
                currentQuery = newText.ifEmpty { "a" }
                viewModel.searchMovies(query = currentQuery)

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
        const val CURRENT_SEARCH_QUERY = "searchQuery"
    }
}