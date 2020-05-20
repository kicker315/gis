package com.zydcc.zrdc.ui.mapmode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.esri.arcgisruntime.geometry.GeometryEngine
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.zydcc.zrdc.utilities.PositionUtil

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/20:11:00 AM
 * ========================================
 */
class MapViewModel internal constructor(
    view: Map,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var mView = view

    // 图层编号 0 默认影像图 1 谷歌影像图
    var layerCode = 0
    // 当前坐标
    var currentPt: Point? = null

    // 百度回调监听
    var bdListener = MyBDLocationListener()


    var onLocationCallback: (Point) -> Unit =  {}

    var onErrorCallback: (String) -> Unit = {}


    inner class MyBDLocationListener : BDAbstractLocationListener() {

        override fun onReceiveLocation(bdLocation: BDLocation?) {
            if (bdLocation != null && bdLocation.locType != BDLocation.TypeServerError) {
                // 获取纬度信息
                val latitude = bdLocation.latitude
                // 获取经度信息
                val longitude = bdLocation.longitude
                val gps = PositionUtil.gcj_To_Gps84(latitude, longitude)
                val d1 = gps.wgLon
                val d2 = gps.wgLat
                val point =
                    when(layerCode) {
                        0 -> Point(d1, d2, SpatialReferences.getWgs84())
                        1 -> Point(longitude, latitude, SpatialReferences.getWgs84())
                        else -> null
                    }
                val p = GeometryEngine.project(point, SpatialReferences.getWebMercator())
                currentPt = p.extent.center
                currentPt?.let {
                    // 定位
                    onLocationCallback.invoke(it)
                }
            } else {
                // Error
                onErrorCallback.invoke("定位出错，请检查定位权限是否授予")
            }

        }

    }



}