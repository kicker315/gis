package com.ningsk.zrdc.entity.dic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/1:8:29 AM
 * ========================================
 */
@Entity(tableName = "DLTB")
data class Dltb(
    @PrimaryKey @ColumnInfo(name="id") val id: Int,
    val zdmc: String = "",
    val zddm: String = "",
    val ystj: Int = 1,
    val zdlx: String = "",
    val bz: String = "",
    val xsws: String = "",
    val xh: Int = 1,
    val sfxs: Int = 1
)