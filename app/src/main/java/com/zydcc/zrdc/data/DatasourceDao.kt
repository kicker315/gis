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
interface DatasourceDao {
    @Query("SELECT * FROM data_source WHERE type = 0 ORDER BY id")
    fun getShpDatasourceList(): LiveData<List<Datasource>>

    @Query("SELECT * FROM data_source WHERE type = 1 ORDER BY id")
    fun getGeoDatasourceList(): LiveData<List<Datasource>>

    @Query("SELECT * FROM data_source WHERE type = 2 ORDER BY id")
    fun getTpkDatasourceList(): LiveData<List<Datasource>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(datasource: Datasource)

    @Delete
    suspend fun delete(datasource: Datasource)
}