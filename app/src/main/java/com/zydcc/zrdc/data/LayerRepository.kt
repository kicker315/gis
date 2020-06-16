package com.zydcc.zrdc.data

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:10:43 AM
 * ========================================
 */
class LayerRepository private constructor(private val dao: LayerDao) {

    fun getShpDatasourceList() = dao.getShpDatasourceList()

    fun getTpkDatasourceList() = dao.getTpkDatasourceList()

    suspend fun insert(layer: Layer) {
        dao.insert(layer)
    }

    suspend fun remove(layer: Layer) {
        dao.delete(layer)
    }

    companion object {
        // For Single instantiation
        @Volatile private var instance: LayerRepository ?= null

        fun getInstance(datasourceDao: LayerDao) =
            instance?: synchronized(this) {
                instance?: LayerRepository(datasourceDao).also {
                    instance = it
                }
            }

    }

}