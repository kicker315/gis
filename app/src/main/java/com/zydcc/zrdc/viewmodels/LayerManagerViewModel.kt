package com.zydcc.zrdc.viewmodels

import androidx.lifecycle.*
import com.zydcc.zrdc.core.ext.postNext
import com.zydcc.zrdc.core.ext.setNext
import com.zydcc.zrdc.data.Datasource
import com.zydcc.zrdc.data.DatasourceRepository
import kotlinx.coroutines.launch

/**
 * =======================================
 * 图层管理
 * Create by ningsikai 2020/5/25:10:50 AM
 * ========================================
 */
class LayerManagerViewModel(
    private val repository: DatasourceRepository
): ViewModel() {

    val shpDatasourceList: LiveData<List<Datasource>> =
        repository.getShpDatasourceList()

    val geoDatasourceList: LiveData<List<Datasource>> =
        repository.getGeoDatasourceList()

    val tpkDatasourceList: LiveData<List<Datasource>> =
        repository.getTpkDatasourceList()


    // 添加数据源
    fun addDatasource(datasource: Datasource) {
        viewModelScope.launch {
            repository.insert(datasource)
        }
    }

    // 删除数据源
    fun deleteDatasource(datasource: Datasource) {
        viewModelScope.launch {
            repository.remove(datasource)
        }
    }

}




class LayerManagerViewModelFactory(private val repository: DatasourceRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LayerManagerViewModel(repository) as T
    }
}