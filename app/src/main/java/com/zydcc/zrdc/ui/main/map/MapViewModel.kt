package com.zydcc.zrdc.ui.main.map

import android.app.Application
import androidx.lifecycle.*
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.esri.arcgisruntime.geometry.GeometryEngine
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.zydcc.zrdc.core.ext.postNext
import com.zydcc.zrdc.core.ext.setNext
import com.zydcc.zrdc.db.AppDatabase
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.utils.PositionUtil
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/20:11:00 AM
 * ========================================
 */
class MapViewModel internal constructor(
    application: Application
): AndroidViewModel(application) {

    // 图层编号 0 默认影像图 1 谷歌影像图
    var layerCode = 0
    // 当前坐标
    var currentPt: Point? = null

    private val dataBase = AppDatabase.getInstance(application)





    // 百度回调监听
    var bdListener = MyBDLocationListener()

    private var _viewStateLiveData = MutableLiveData(MapViewState.initial())
    val viewStateLiveData: LiveData<MapViewState> = _viewStateLiveData

    var onLocationCallback: (Point) -> Unit =  {}

    var onErrorCallback: (String) -> Unit = {}

    val shpDatasourceList: LiveData<List<Layer>> =
        dataBase.layerDao().getShpDatasourceList()


    val tpkDatasourceList: LiveData<List<Layer>> =
        dataBase.layerDao().getTpkDatasourceList()


    inner class MyBDLocationListener : BDAbstractLocationListener() {

        override fun onReceiveLocation(bdLocation: BDLocation?) {
            val df = DecimalFormat("0.000")
            df.roundingMode = RoundingMode.HALF_UP
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
                        0 -> {
                            _viewStateLiveData.setNext { last ->
                                last.copy(
                                    latitude = df.format(d2),
                                    longitude = df.format(d1)
                                )
                            }
                            Point(d1, d2, SpatialReferences.getWgs84())
                        }
                        1 -> {
                            _viewStateLiveData.setNext { last ->
                                last.copy(
                                    latitude = df.format(latitude),
                                    longitude = df.format(longitude)
                                )
                            }
                            Point(longitude, latitude, SpatialReferences.getWgs84())
                        }
                        else -> null
                    }
                val p = GeometryEngine.project(point, SpatialReferences.getWebMercator())
                currentPt = p.extent.center
                currentPt?.let { it ->
                    // 定位
                    _viewStateLiveData.postNext { last ->
                        last.copy(
                            x = df.format(it.x),
                            y = df.format(it.y)
                        )
                    }
                    onLocationCallback.invoke(it)
                }
            } else {
                // Error
                onErrorCallback.invoke("定位出错，请检查定位权限是否授予")
            }

        }

    }

}

