package com.zydcc.zrdc.entity.dic

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/14:9:13 AM
 * ========================================
 */
@Parcelize
@Entity(foreignKeys = [(
        ForeignKey(entity = Layer::class,
            parentColumns = ["id"],
            childColumns = ["layerId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
        )],
    primaryKeys = ["id", "layerId"],
    indices = [Index(value=["layerId"], unique = false)]
)
data class ShowField(
    var id: Int,
    var fieldName: String,
    var layerId: Int
): Parcelable