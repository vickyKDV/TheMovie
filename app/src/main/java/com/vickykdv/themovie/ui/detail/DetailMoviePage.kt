package com.vickykdv.themovie.ui.detail

import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.vickykdv.themovie.BuildConfig.imageUrl
import com.vickykdv.themovie.R
import com.vickykdv.themovie.base.BaseActivity
import com.vickykdv.themovie.database.LocalDataMapping
import com.vickykdv.themovie.databinding.ActivityDetailBinding
import com.vickykdv.themovie.model.detailmovie.ResponseDetailMovie
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.state.DetailState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMoviePage : BaseActivity() {

    private val binding  by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val data : DataMovie? by lazy {
        intent.getParcelableExtra("data")
    }

    private val dataLocal by lazy {
        LocalDataMapping.mapResponseToEntity(data!!)
    }


    private val viewModel: DetailViewModel by viewModels()

    override fun setLayout() = binding.root

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

        }
        setupUI()
    }



    private fun setupUI(){
        binding.imgBack.setOnClickListener {
            finish()
        }
        setupViewModel()
    }


    private fun setupViewModel(){
        viewModel.state.observe(this, {
            when (it) {
                is DetailState.Loading -> setLoading(true)
                is DetailState.Result -> successGetDetailMovie(it.data)
                is DetailState.Error -> showError()
            }
        })


        binding.imgFav.setOnClickListener {
            viewModel.addToFavorite(dataLocal)
        }

        viewModel.stateFavorite.observe(this, {
            when (it) {
                true -> isFavorite()
                false -> notFavorite()
            }
        })

        viewModel.checkFavorite(dataLocal)
        viewModel.getDetailMovie(data!!.id)
    }

    private fun isFavorite() {
        binding.imgFav.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_baseline_favorite_24
            )
        )
    }

    private fun notFavorite() {
        binding.imgFav.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_baseline_favorite_border_24
            )
        )
    }


    private fun successGetDetailMovie(response: ResponseDetailMovie) {
        setLoading(false)
        with(binding) {
            txtTitle.text = response.title
            txtReleaseDate.text = response.release_date.toString()
            txtDescription.text = response.overview

            Glide.with(this@DetailMoviePage)
                .load(imageUrl + response.poster_path)
                .into(imgPoster)

            Glide.with(this@DetailMoviePage)
                .load(imageUrl + response.backdrop_path)
                .into(imgBackground)

            show(rlBackground)
        }
    }


    private fun setLoading(isload: Boolean){
        with(binding){
            if(isload){
                show(rlPlace)
                hide(constData)
            }else{
                hide(rlPlace)
                show(constData)
            }
        }
    }


}