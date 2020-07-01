package com.zydcc.zrdc.ui.main.query

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/1:11:19 AM
 * ========================================
 */
data class QueryViewState(
    var layerName: String = "",
    var layerUrl: String = "",
    var selectFieldNames: String = "",
    var selectFieldValues: String = "",
    var fieldName: String = "",
    var fieldValue: String = "",
    var operaName: String = "",
    var operaValue: String = "",
    var range: String = ""
)