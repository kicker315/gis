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

): Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0
}