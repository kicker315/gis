package com.zydcc.zrdc.model.bean

import androidx.databinding.ObservableField

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/18:9:59 AM
 * ========================================
 */
data class QueryData(
    var layerName: ObservableField<String> = ObservableField(""),
    var layerUrl: ObservableField<String> = ObservableField(""),
    var selectFieldNames: ObservableField<String> = ObservableField(""),
    var selectFieldValues: ObservableField<String> = ObservableField(""),
    var fieldName: ObservableField<String> = ObservableField(""),
    var fieldValue: ObservableField<String> = ObservableField(""),
    var operaName: ObservableField<String> = ObservableField(""),
    var operaValue: ObservableField<String> = ObservableField(""),
    var range: ObservableField<String> = ObservableField("")
)