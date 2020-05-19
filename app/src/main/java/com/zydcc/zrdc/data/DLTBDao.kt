package com.zydcc.zrdc.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * =======================================
 * The Data Access Object for the DLTB class
 * Create by ningsikai 2020/5/18:3:03 PM
 * ========================================
 */
@Dao
interface DLTBDao {
    @Query("SELECT * FROM DLTB ORDER BY zdmc")
    fun getDLTBList(): LiveData<List<DLTB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dltbs: List<DLTB>)
}