package com.vickykdv.themovie.ui.splash

import android.os.Bundle
import android.view.WindowManager
import com.vickykdv.themovie.base.BaseActivity
import com.vickykdv.themovie.databinding.ActivitySplashBinding
import com.vickykdv.themovie.ui.dashboard.DashboardPage

class SplashActivity : BaseActivity() {

    private val binding  by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun setLayout()=binding.root

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        handlerDelay {
            openActivity(DashboardPage::class.java,true)
        }
    }

}