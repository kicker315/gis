package com.zydcc.zrdc.base

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.zydcc.iframe.app.BaseApp
import com.zydcc.zrdc.location.LocationService

/**
 * =======================================
 * App
 * Create by ningsikai 2020/5/19:1:17 PM
 * ========================================
 */
class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        locationService = LocationService(applicationContext)
    }

    companion object {
        // 百度定位监听
        var locationService: LocationService? = null
    }

}