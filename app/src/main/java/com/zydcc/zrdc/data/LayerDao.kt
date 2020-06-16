package com.zydcc.zrdc.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:10:34 AM
 * ========================================
 */

@Dao
interface LayerDao {
    @Query("SELECT * FROM LAYER WHERE isBaseMap  = 1 ORDER BY id")
    fun getShpDatasourceList(): LiveData<List<Layer>>

    @Query("SELECT * FROM LAYER WHERE isBaseMap = 0 ORDER BY id")
    fun getTpkDatasourceList(): LiveData<List<Layer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(layer: Layer)

    @Delete
    suspend fun delete(layer: Layer)
}