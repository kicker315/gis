package com.ningsk.zrdc.ui.listener

import android.content.Context
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener
import com.esri.arcgisruntime.mapping.view.MapView

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/17:10:20 AM
 * ========================================
 */
class FreehandPolygonDrawingListener(context: Context, mapView: MapView) : DefaultMapViewOnTouchListener(context, mapView) {

    companion object {
       @Volatile private var instance: FreehandPolygonDrawingListener ?= null
       fun getInstance(context: Context, mapView: MapView) {
           instance?: synchronized(this) {
               instance?: FreehandPolygonDrawingListener(context, mapView).also {
                   instance = it
               }
           }
       }
    }

}