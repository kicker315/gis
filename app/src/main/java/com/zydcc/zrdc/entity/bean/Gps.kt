package com.zydcc.zrdc.entity.bean

/**
 * =======================================
 * 自定义点
 * Create by ningsikai 2020/5/20:11:15 AM
 * ========================================
 */
class Gps () {

    constructor(wgLat: Double, wgLon: Double): this() {
        this.wgLat = wgLat
        this.wgLon = wgLon
    }

    // 纬度
    var wgLat: Double = 0.0
    // 经度
    var wgLon: Double = 0.0
}