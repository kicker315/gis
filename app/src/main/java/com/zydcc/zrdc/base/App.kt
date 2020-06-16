package com.zydcc.zrdc.base

import androidx.multidex.MultiDexApplication
import com.zydcc.zrdc.location.LocationService

/**
 * =======================================
 * App
 * Create by ningsikai 2020/5/19:1:17 PM
 * ========================================
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        locationService = LocationService(applicationContext)
    }

    companion object {
        // 百度定位监听
        var locationService: LocationService? = null
    }

}