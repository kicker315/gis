package com.zydcc.zrdc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * =======================================
 * 数据源
 * Create by ningsikai 2020/5/19:3:05 PM
 * ========================================
 */
@Entity(tableName = "data_source")
data class Datasource(
    @PrimaryKey @ColumnInfo(name="id") val id: Int,
    val mc: String,
    val dm: String,
    val type: Int, // 0 Tpk 1 Shp 2 Geodatabase
    val sfxs: Int = 1
)