package com.zydcc.zrdc.viewmodels

import androidx.lifecycle.*
import com.zydcc.zrdc.core.ext.postNext
import com.zydcc.zrdc.core.ext.setNext
import com.zydcc.zrdc.data.DLTB
import com.zydcc.zrdc.data.DLTBRepository
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
    private val repository: DatasourceRepository,
    private val dltbRepository: DLTBRepository
): ViewModel() {

    val shpDatasourceList: LiveData<List<Datasource>> =
        repository.getShpDatasourceList()


    val tpkDatasourceList: LiveData<List<Datasource>> =
        repository.getTpkDatasourceList()

    val dltbList: LiveData<List<DLTB>> =
        dltbRepository.getDLTBList()


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




class LayerManagerViewModelFactory(
    private val repository: DatasourceRepository,
    private var dltbRepository: DLTBRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LayerManagerViewModel(repository, dltbRepository) as T
    }
}