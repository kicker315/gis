package com.zydcc.zrdc.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/16:10:57 AM
 * ========================================
 */
@Parcelize
data class ProjectFile(
    var createTime: Date,
    var featureId: String,
    var fileName: String,
    var fileType: Int,
    var fileURL: String,
    var fwj: Float,
    var lat: Float,
    var lon: Float,
    var projectId: Long,
    var timeLength: Long,
    var xzb: Float,
    var yzb: Float
): Parcelable