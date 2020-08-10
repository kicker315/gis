package com.ningsk.zrdc

import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.Utils
import com.ningsk.zrdc.service.LocationService

/**
 * =======================================
 * App
 * Create by ningsikai 2020/5/19:1:17 PM
 * ========================================
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        CrashUtils.init()
        // 百度定位监听
        locationService = LocationService(applicationContext)
    }


    companion object {
        var locationService: LocationService? = null
    }





}