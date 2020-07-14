package com.zydcc.zrdc.entity.dic

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/1:8:29 AM
 * ========================================
 */
@Entity(tableName = "LAYER")
@Parcelize
data class Layer(
    var fillColor: String,
    var isBaseMap: Int, // 0 操作图层 1 底图
    var isEdit: Int, // 0 不可编辑 1 可编辑
    var isLabel: Int?,
    var isSelect: Int, // 0 未选中 1 选中
    var isShow: Int, // 0 显示 1 隐藏
    var labelColor: String = "",
    var layerId: Long,
    var layerIndex: Int = -1,
    var layerName: String,
    var layerType: String = "",
    var layerUrl: String,
    var lineColor: String = "",
    var projectId: Long

): Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0
}