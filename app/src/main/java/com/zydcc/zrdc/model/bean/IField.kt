package com.zydcc.zrdc.model.bean

import com.esri.arcgisruntime.data.Field
import com.zydcc.zrdc.model.dic.DLTB


/**
 * =======================================
 *
 * Create by ningsikai 2020/6/16:3:38 PM
 * ========================================
 */
data class IField(
    var checked: Int,
    var dltb: DLTB?,
    var field: Field
)