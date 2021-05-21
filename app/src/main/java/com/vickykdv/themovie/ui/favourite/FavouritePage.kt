package com.vickykdv.themovie.ui.favourite

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vickykdv.themovie.R
import com.vickykdv.themovie.base.BaseActivity
import com.vickykdv.themovie.database.LocalDataMapping
import com.vickykdv.themovie.database.MovieEntity
import com.vickykdv.themovie.databinding.ActivityDashboardBinding
import com.vickykdv.themovie.databinding.ActivityFavouriteBinding
import com.vickykdv.themovie.ui.adapter.FavouriteAdapter
import com.vickykdv.themovie.ui.detail.DetailMoviePage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritePage:BaseActivity() {

    private val viewModel: FavouriteViewModel by viewModels()

    private val adapter  by lazy {
        FavouriteAdapter { item -> showDetail(item)}
    }

    private val binding  by lazy {
        ActivityDashboardBinding.inflate(layoutInflater)
    }

    override fun setLayout()= binding.root

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

        }
        setupToolbar(
           setupToolBar =  binding.toolbar,
            titleTolbar = resources.getString(R.string.favourite),
            showTitle = true,
            needHomeButton = true

        )
        setupUI()
    }


    private fun setupUI(){

        with(binding){
            hide(cvCategory)
            hide(shimmerMovie)
            rvData.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this@FavouritePage)
                it.setHasFixedSize(true)
            }
        }

        setupViewModel()

    }

    private fun showDetail(item: MovieEntity) {
        val dataMovie = LocalDataMapping.mapEntityToResponse(item)
        openActivityResult(DetailMoviePage::class.java, extras = {
            this.putParcelable("data",dataMovie)
        })
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModel() {
        viewModel.data.observe(this,(adapter::submitList))
        viewModel.getFavoriteMovie()
    }


}