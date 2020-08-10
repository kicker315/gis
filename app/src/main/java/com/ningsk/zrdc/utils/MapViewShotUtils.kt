package com.ningsk.zrdc.utils

import android.content.Context
import android.media.MediaActionSound
import android.media.MediaActionSound.SHUTTER_CLICK
import com.esri.arcgisruntime.mapping.view.MapView
import java.io.File

/**
 * =======================================
 * 截屏工具类
 * Create by ningsikai 2020/6/17:2:59 PM
 * ========================================
 */
object MapViewShotUtils {


    fun shot(mapView: MapView, context: Context) {
        val listenableFuture =  mapView.exportImageAsync()
        listenableFuture.addDoneListener {
            val bitmap = listenableFuture.get()
            MediaActionSound().play(SHUTTER_CLICK)
        }
    }

}