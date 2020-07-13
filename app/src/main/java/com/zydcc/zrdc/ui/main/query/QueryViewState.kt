package com.zydcc.zrdc.ui.main.query

import com.esri.arcgisruntime.data.Field
import com.zydcc.zrdc.entity.dic.Layer

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/1:11:19 AM
 * ========================================
 */
data class QueryViewState(
    var layer: Layer ?= null,
    var showFields: MutableList<Field> = mutableListOf(),
    var field: Field ?= null,
    var operaName: String = "",
    var operaValue: String = "",
    var range: String = ""
)