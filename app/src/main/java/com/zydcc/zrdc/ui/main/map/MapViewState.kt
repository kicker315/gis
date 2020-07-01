package com.zydcc.zrdc.ui.main.map

import androidx.lifecycle.LiveData

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/1:11:10 AM
 * ========================================
 */
data class MapViewState(
    var longitude: String,
    var latitude: String,
    var x: String,
    var y: String
) {
    companion object {
        fun initial(): MapViewState {
            return MapViewState(
                longitude = "",
                latitude = "",
                x = "",
                y = ""
            )
        }
    }
}