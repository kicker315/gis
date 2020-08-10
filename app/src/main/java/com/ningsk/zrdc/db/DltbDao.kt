package com.ningsk.zrdc.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ningsk.zrdc.entity.dic.Dltb

/**
 * =======================================
 * The Data Access Object for the DLTB class
 * Create by ningsikai 2020/5/18:3:03 PM
 * ========================================
 */
@Dao
interface DltbDao {
    @Query("SELECT * FROM DLTB ORDER BY zdmc")
    fun getAll(): LiveData<List<Dltb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<Dltb>)
}