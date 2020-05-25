package com.zydcc.zrdc.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.zydcc.zrdc.R
import com.zydcc.zrdc.databinding.ActivitySplashBinding

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:9:43 AM
 * ========================================
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
    }

}