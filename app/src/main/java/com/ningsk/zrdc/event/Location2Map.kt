package com.ningsk.zrdc.event

import com.esri.arcgisruntime.data.Feature
import com.esri.arcgisruntime.data.Field
import com.ningsk.zrdc.entity.dic.Layer

/**
 * =======================================
 * 定位到底图
 * Create by ningsikai 2020/8/3:9:00 AM
 * ========================================
 */
data class Location2Map(
    var featureList: List<Feature> = mutableListOf(), // 搜索的结果集
    var showField: List<Field> = mutableListOf(), // 显示的字段集
    var layer: Layer?= null // 显示的图层
)

