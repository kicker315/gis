package com.zydcc.zrdc.model.bean

import androidx.databinding.ObservableField

/**
 * =======================================
 * 位置信息
 * Create by ningsikai 2020/5/20:4:11 PM
 * ========================================
 */
data class LocationData(
    var longitude: ObservableField<String>,
    var latitude: ObservableField<String>,
    var x: ObservableField<String>,
    var y: ObservableField<String>
)