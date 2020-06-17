package com.zydcc.zrdc.ui.listener

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.zydcc.zrdc.utilities.MapUtils
import java.text.NumberFormat
import kotlin.math.roundToInt

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/17:11:28 AM
 * ========================================
 */
class MeasureDistanceListener(context: Context,
                              mapView: MapView,
                              relativeLayout: RelativeLayout,
                              textView: TextView
) : DefaultMapViewOnTouchListener(context, mapView) {


    private var ctx = context
    private var rlt = relativeLayout
    private var tv = textView
    private var lastLength = 0.0
    private var length = 0.0
    private var points = mutableListOf<Point>()

    override fun onRotate(event: MotionEvent?, rotationAngle: Double): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        val screenPoint = android.graphics.Point(e.x.roundToInt(), e.y.roundToInt())
        val point = mMapView.screenToLocation(screenPoint)
        points.add(point)
        val size = points.size
        if (size >= 2) {
            lastLength = MapUtils.drawLineAndMeasure(points[size-2], points[size-1], mMapView, SimpleLineSymbol.Style.SOLID)
            length += lastLength
            val numberFormat = NumberFormat.getInstance()
            numberFormat.maximumFractionDigits = 2
            val str = numberFormat.format(length)
            val sb = StringBuilder()
            sb.append("测量距离为:")
                .append(str)
                .append("米")
            tv.text = sb.toString()
            tv.setPadding(0, 15, 0, 0)
            tv.textSize = 15f
            if (rlt.visibility == View.GONE) {
                rlt.visibility = View.VISIBLE
            }
        } else if (size == 1) {
            MapUtils.drawSinglePoint(point, mMapView)
        }
        return true
    }

    fun pointClear() {
        MapUtils.MeasureClear(points, mMapView, ctx)
        length = 0.0
        lastLength = 0.0

    }

    companion object {
        @Volatile private var instance: MeasureDistanceListener ?= null
        fun getInstance(context: Context, mapView: MapView, relativeLayout: RelativeLayout, textView: TextView): MeasureDistanceListener {
            synchronized(this) {
                return instance ?: MeasureDistanceListener(context,mapView, relativeLayout, textView).also {
                    instance = it
                }
            }
        }
    }

}