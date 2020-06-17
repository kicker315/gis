package com.zydcc.zrdc.data

import androidx.annotation.NonNull
import androidx.annotation.Nullable
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