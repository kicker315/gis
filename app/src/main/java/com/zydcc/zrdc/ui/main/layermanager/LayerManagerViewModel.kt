package com.zydcc.zrdc.ui.main.layermanager

import android.app.Application
import androidx.lifecycle.*
import com.zydcc.zrdc.db.AppDatabase
import com.zydcc.zrdc.entity.dic.Layer
import kotlinx.coroutines.launch

/**
 * =======================================
 * 图层管理
 * Create by ningsikai 2020/5/25:10:50 AM
 * ========================================
 */
class LayerManagerViewModel(application: Application
): AndroidViewModel(application) {

    val dataBase = AppDatabase.getInstance(application)

    val shpDatasourceList: LiveData<List<Layer>> =
        dataBase.layerDao().getShpDatasourceList()



    val tpkDatasourceList: LiveData<List<Layer>> =
        dataBase.layerDao().getTpkDatasourceList()




    // 添加数据源
    fun addDatasource(datasource: Layer) {
        viewModelScope.launch {
            dataBase.layerDao().insert(datasource)
        }
    }

    // 删除数据源
    fun deleteDatasource(datasource: Layer) {
        viewModelScope.launch {
            dataBase.layerDao().delete(datasource)
        }
    }

    fun updateDatasource(datasource: Layer) {
        viewModelScope.launch {
            dataBase.layerDao().update(datasource)
        }
    }

}
