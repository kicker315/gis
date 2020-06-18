package com.zydcc.zrdc.db.repository

import com.zydcc.zrdc.db.dao.DLTBDao

/**
 * =======================================
 * Repository module for handling data operations
 * Create by ningsikai 2020/5/18:3:09 PM
 * ========================================
 */
class DLTBRepository private constructor(private val dltbDao: DLTBDao) {

    fun getDLTBList() = dltbDao.getDLTBList()

    companion object {
        // For Single instantiation
        @Volatile private var instance: DLTBRepository?= null

        fun getInstance(dltbDao: DLTBDao) =
            instance ?: synchronized(this) {
                instance
                    ?: DLTBRepository(dltbDao).also {
                    instance = it
                }
            }

    }

}