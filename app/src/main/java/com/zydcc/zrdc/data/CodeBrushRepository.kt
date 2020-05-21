package com.zydcc.zrdc.data

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/21:2:51 PM
 * ========================================
 */
class CodeBrushRepository private constructor(private val codeBrushDao: CodeBrushDao) {

    fun getCodeBrushList() = codeBrushDao.getCodeBrushList()

    companion object {
        // For Single instantiation
        @Volatile private var instance: CodeBrushRepository ?= null

        fun getInstance(codeBrushDao: CodeBrushDao) =
            instance?: synchronized(this) {
                instance?: CodeBrushRepository(codeBrushDao).also {
                    instance = it
                }
            }

    }

}