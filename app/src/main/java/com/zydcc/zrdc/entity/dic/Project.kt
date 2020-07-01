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
@Entity(tableName = "PROJECT")
data class Project(
    var projectName: String = "",
    var projectMan: String = "",
    var projectProgress: String = "",
    var startTime: Date = Date(),
    var lastEditTime: Date = Date(),
    var url: String = "",
    var featureNum: Int = 0,
    var doneFeatureNum: Int = 0,
    var coordinate: String = ""
)  {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0
}