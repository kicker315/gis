package com.zydcc.zrdc.utils

import com.esri.arcgisruntime.location.LocationDataSource

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/2:3:01 PM
 * ========================================
 */
class MyLocationDataSource : LocationDataSource() {

    fun UpdateLocation(location: Location) {
        updateLocation(location)
    }


    override fun onStop() {

    }

    override fun onStart() {
        onStartCompleted(null)
    }

}