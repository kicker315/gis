package com.zydcc.zrdc.data

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:10:43 AM
 * ========================================
 */
class DatasourceRepository private constructor(private val dao: DatasourceDao) {

    fun getShpDatasourceList() = dao.getShpDatasourceList()

    fun getTpkDatasourceList() = dao.getTpkDatasourceList()

    suspend fun insert(datasource: Datasource) {
        dao.insert(datasource)
    }

    suspend fun remove(datasource: Datasource) {
        dao.delete(datasource)
    }

    companion object {
        // For Single instantiation
        @Volatile private var instance: DatasourceRepository ?= null

        fun getInstance(datasourceDao: DatasourceDao) =
            instance?: synchronized(this) {
                instance?: DatasourceRepository(datasourceDao).also {
                    instance = it
                }
            }

    }

}