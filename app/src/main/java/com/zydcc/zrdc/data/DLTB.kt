package com.zydcc.zrdc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/18:2:58 PM
 * ========================================
 */
@Entity(tableName = "DLTB")
data class DLTB(
    @PrimaryKey @ColumnInfo(name="id") val id: Int,
    val zdmc: String,
    val zddm: String,
    val zdlx: String,
    val sfxs: Int = 1
)