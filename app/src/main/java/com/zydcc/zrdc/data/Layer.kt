package com.zydcc.zrdc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/16:12:32 PM
 * ========================================
 */
@Entity(tableName = "LAYER")
data class Layer(
    var fillColor: String,
    var isBaseMap: Int,
    var isEdit: Int,
    var isLabel: Int?,
    var isSelect: Int,
    var isShow: Int,
    var labelColor: String = "",
    var labelField: String = "",
    var layerId: Long,
    var layerIndex: Int = -1,
    var layerName: String,
    var layerType: String = "",
    var layerUrl: String,
    var lineColor: String = "",
    var projectId: Long

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0
}