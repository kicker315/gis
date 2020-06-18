package com.zydcc.zrdc.db.table

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
    @PrimaryKey @ColumnInfo(name="ID") var id: Int,
    @ColumnInfo(name="DM")var dm: String,
    @ColumnInfo(name="MC")var mc: String,
    @ColumnInfo(name="YJLBM")val yjlbm: String ?= null,
    @ColumnInfo(name="RGB")val rgb: String ?= null
)