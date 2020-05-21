package com.zydcc.zrdc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * =======================================
 * 编码刷内容
 * Create by ningsikai 2020/5/21:2:44 PM
 * ========================================
 */
@Entity(tableName = "TDDCGZFL")
data class CodeBrush(
    @PrimaryKey @ColumnInfo(name="ID") val id: Int,
    @ColumnInfo(name="DM")val dm: String,
    @ColumnInfo(name="MC")val mc: String,
    @ColumnInfo(name="YJLBM")val yjlbm: String?,
    @ColumnInfo(name="RGB")val rgb: String?
)