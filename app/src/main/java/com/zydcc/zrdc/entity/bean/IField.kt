package com.zydcc.zrdc.entity.bean

import com.esri.arcgisruntime.data.Field
import com.zydcc.zrdc.entity.dic.Dltb

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/16:3:38 PM
 * ========================================
 */
data class IField(
    var checked: Int,
    var dltb: Dltb?,
    var field: Field
)