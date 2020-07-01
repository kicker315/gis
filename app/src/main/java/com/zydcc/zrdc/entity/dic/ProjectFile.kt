package com.zydcc.zrdc.entity.dic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/1:8:29 AM
 * ========================================
 */
@Entity
data class ProjectFile(
    var projectId: Long,
    var featureId: String,
    var fileName: String,
    var fileUrl: String,
    var fileType: String,
    var createTime: Date,
    var lon: Float,
    var lat: Float,
    var xzb: Float,
    var yzb: Float,
    var fwj: Float,
    var timeLength: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0
}