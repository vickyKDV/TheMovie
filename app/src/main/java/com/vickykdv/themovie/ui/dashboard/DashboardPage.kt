package com.vickykdv.themovie.ui.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pixplicity.easyprefs.library.Prefs
import com.vickykdv.themovie.R
import com.vickykdv.themovie.base.BaseActivity
import com.vickykdv.themovie.base.BaseConstants
import com.vickykdv.themovie.base.BaseUtils
import com.vickykdv.themovie.databinding.ActivityDashboardBinding
import com.vickykdv.themovie.databinding.BottomDlgCategoryBinding
import com.vickykdv.themovie.model.movie.DataMovie
import com.vickykdv.themovie.network.state.MovieState
import com.vickykdv.themovie.ui.adapter.MovieAdapter
import com.vickykdv.themovie.ui.detail.DetailMoviePage
import com.vickykdv.themovie.ui.favourite.FavouritePage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardPage:BaseActivity() {

    private val viewModel: DashboardViewModel by viewModels()

    private val adapter  by lazy {
        MovieAdapter { item -> showDetail(item)}
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
            titleTolbar = resources.getString(R.string.app_name),
            showTitle = true,
            needHomeButton = false

        )
        setupUI()
    }


    private fun setupUI(){

        val category = Prefs.getString(BaseConstants.PREF_CATEGORY,"Popular")
        with(binding){
            show(cvCategory)
            rvData.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this@DashboardPage)
                it.setHasFixedSize(true)
            }

            txtCategory.text = category
            cvCategory.setOnClickListener { dialogCategory() }
        }

        setupData(category)
        setupViewModel()


    }

    private fun showDetail(item: DataMovie) {
        openActivity(DetailMoviePage::class.java, extras = {
            this.putParcelable("data",item)
        })
    }

    private fun setupData(category:String = "popular"){
        when(category){
            "Popular" -> {
                viewModel.getPopularData()
            }
            "Top Rated" -> {
                viewModel.getTopRatedData()
            }
            "Upcoming" -> {
                viewModel.getUpcomingData()
            }
            "Now Playing" -> {
                viewModel.getNowPlaying()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.nav_favourite -> {
                logDebug("Fav")
                openActivity(FavouritePage::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModel(){
        viewModel.state.observe(this,{
            when(it){
                is MovieState.Loading -> setLoading(true)
                is MovieState.Result -> setLoading(false)
                is MovieState.Error -> showError(refreshClick = {
                    setupViewModel()
                })
            }
        })
        viewModel.data.observe(this, Observer(adapter::submitList))
    }

    private fun setLoading(isload: Boolean){
        with(binding){
            if(isload){
                show(shimmerMovie)
                hide(rvData)
            }else{
                hide(shimmerMovie)
                show(rvData)
            }
        }
    }

    private fun dialogCategory() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val binding : BottomDlgCategoryBinding = BottomDlgCategoryBinding.inflate(layoutInflater)

        with(binding) {
            txtNowPlaying.setOnClickListener {
                dialog.dismiss()
                logDebug("Now Playing")
                setPrefs("Now Playing")
                setupData("Now Playing")

            }
            txtPopular.setOnClickListener {
                dialog.dismiss()
                logDebug("Popular")
                setPrefs("Popular")
                setupData("Popular")

            }
            txtTopRated.setOnClickListener {
                dialog.dismiss()
                logDebug("Top Rated")
                setPrefs("Top Rated")
                setupData("Top Rated")

            }
            txtUpComing.setOnClickListener {
                dialog.dismiss()
                logDebug("Upcoming")
                setPrefs("Upcoming")
                setupData("Upcoming")

            }

        }

        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        BaseUtils.hideNavigationBar(this,dialog)

    }

    private fun setPrefs(category: String){
        Prefs.putString(BaseConstants.PREF_CATEGORY,category)
        binding.txtCategory.text = category
    }
}