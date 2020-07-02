package com.zydcc.zrdc.ui.listener

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener
import com.esri.arcgisruntime.mapping.view.MapView
import com.zydcc.zrdc.utils.MapUtils
import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/16:2:58 PM
 * ========================================
 */
class MeasureAreaListener(context: Context,
                          mapView: MapView,
                          relativeLayout: RelativeLayout,
                          textView: TextView
) : DefaultMapViewOnTouchListener(context, mapView) {

    private var area = 0.0
    private var points = mutableListOf<Point>()
    private var ctx =  context
    private var rlt = relativeLayout
    private var tv = textView

    override fun onRotate(event: MotionEvent?, rotationAngle: Double): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        val screenPoint = android.graphics.Point(e.x.roundToInt(), e.y.roundToInt())
        val point =  mMapView.screenToLocation(screenPoint)
        points.add(point)
        if (points.size >= 2) {
            MapUtils.drawPointAndLine(points, mMapView, true)
        } else if (points.size == 1) {
            MapUtils.drawSinglePoint(points[0], mMapView)
        }

        if (points.size >= 3) {
            area = MapUtils.areaMeasure(points, mMapView)
            area = BigDecimal(area).setScale(2, 4).toDouble()
            val sb = StringBuilder()
            sb.append("测量面积为：")
                .append(abs(area))
                .append("平方米")
            tv.text = sb.toString()
            tv.textSize = 15f
            if (rlt.visibility == View.GONE) {
                rlt.visibility = View.VISIBLE
            }
        }

        return true
    }

    fun pointClear() {
        MapUtils.MeasureClear(points, mMapView, ctx)
        area = 0.0
    }

    companion object {
        @Volatile private var instance: MeasureAreaListener ?= null
        fun getInstance(context: Context,mapView: MapView, relativeLayout: RelativeLayout, textView: TextView): MeasureAreaListener {
            synchronized(this ) {
                return instance ?: MeasureAreaListener(context, mapView,relativeLayout, textView).also {
                    instance = it
                }
            }
        }
    }

}