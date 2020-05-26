package com.zydcc.zrdc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * =======================================
 * 数据源
 * Create by ningsikai 2020/5/25:10:33 AM
 * ========================================
 */
@Entity(tableName = "data_source")
data class Datasource(
    val name: String,
    val path: String,
    val type: Int // 0 shp 1 tpk
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0
}