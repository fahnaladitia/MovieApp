package com.pahnal.submissioncapstone.favorite


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.pahnal.submissioncapstone.R
import com.pahnal.submissioncapstone.core.ui.MovieAdapter
import com.pahnal.submissioncapstone.di.MovieModuleDependencies
import com.pahnal.submissioncapstone.favorite.databinding.ActivityFavoriteBinding
import com.pahnal.submissioncapstone.movie_detail.MovieDetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }
    private lateinit var adapter: MovieAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMovieComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    MovieModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupUI()
    }

    private fun setupUI() {
         adapter = MovieAdapter(
            onClick = { movie ->
                val intent = Intent(this,MovieDetailActivity::class.java)
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
                startActivity(intent)

            },
            onClickButtonFavorite = { _, position ->
                adapter.currentList[position]?.let {
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
        viewModel.movies.observe(this, adapter::submitList)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}