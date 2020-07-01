package com.zydcc.zrdc.entity.dic

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
    @PrimaryKey @ColumnInfo(name="id") var id: Int,
    var zdmc: String,
    var zddm: String,
    var ystj: Int ?= null,
    var zdlx: String ?= null,
    var bz: String ?= null,
    var xsws: String ?= null,
    var xh: Int ?= null,
    var sfxs: Int ?= null
)