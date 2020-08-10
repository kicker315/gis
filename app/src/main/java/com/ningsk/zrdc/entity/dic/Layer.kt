package com.ningsk.zrdc.entity.dic

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
    var isBaseMap: Int, // 0 操作图层 1 底图
    var isEdit: Int, // 0 不可编辑 1 可编辑
    var isLabel: Int = 1, // 0 不显示标注 1 显示标注
    var isSelect: Int, // 0 未选中 1 选中
    var isShow: Int, // 0 显示 1 隐藏
    var fillColor: String = "", // geo的填充颜色
    var lineColor: String = "", // geo的边框颜色
    var labelField: String = "", // 标注内容
    var labelColor: String = "", // 标注的颜色
    var layerId: Long,
    var layerIndex: Int = -1,
    var layerName: String,
    var layerType: String = "",
    var layerUrl: String,
    var projectId: Long

): Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0
}