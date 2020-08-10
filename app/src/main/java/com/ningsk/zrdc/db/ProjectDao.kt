package com.ningsk.zrdc.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ningsk.zrdc.entity.dic.Project

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/1:9:43 AM
 * ========================================
 */
@Dao
interface ProjectDao {
    @Query("SELECT * FROM PROJECT")
    fun getProjectList(): LiveData<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: Project):Long

    @Query("SELECT * from PROJECT where id = :currentId")
    fun getCurrentProject(currentId: Int): LiveData<Project>

}