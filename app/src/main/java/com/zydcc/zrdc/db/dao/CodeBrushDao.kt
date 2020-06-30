package com.zydcc.zrdc.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zydcc.zrdc.db.table.CodeBrush

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/21:2:48 PM
 * ========================================
 */
@Dao
interface CodeBrushDao {
    @Query("SELECT * FROM TDDCGZFL ORDER BY ID")
    fun getCodeBrushList(): LiveData<List<CodeBrush>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dltbs: List<CodeBrush>)
}