package com.zydcc.zrdc.interfaces

import com.zydcc.zrdc.base.BaseView

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/20:4:52 PM
 * ========================================
 */
interface MapOperate : BaseView {

    // 跳转到媒体页面
    fun navToMedia()

    // 定位到当前位置
    fun onLocation()
}