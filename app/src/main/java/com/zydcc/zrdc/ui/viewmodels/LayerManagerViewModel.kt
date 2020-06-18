package com.zydcc.zrdc.ui.viewmodels

import androidx.lifecycle.*
import com.zydcc.zrdc.db.table.DLTB
import com.zydcc.zrdc.db.repository.DLTBRepository
import com.zydcc.zrdc.db.table.Layer
import com.zydcc.zrdc.db.table.LayerRepository
import kotlinx.coroutines.launch

/**
 * =======================================
 * 图层管理
 * Create by ningsikai 2020/5/25:10:50 AM
 * ========================================
 */
class LayerManagerViewModel(
    private val repository: LayerRepository,
    private val dltbRepository: DLTBRepository
): ViewModel() {

    val shpDatasourceList: LiveData<List<Layer>> =
        repository.getShpDatasourceList()


    val tpkDatasourceList: LiveData<List<Layer>> =
        repository.getTpkDatasourceList()

    val dltbList: LiveData<List<DLTB>> =
        dltbRepository.getDLTBList()


    // 添加数据源
    fun addDatasource(datasource: Layer) {
        viewModelScope.launch {
            repository.insert(datasource)
        }
    }

    // 删除数据源
    fun deleteDatasource(datasource: Layer) {
        viewModelScope.launch {
            repository.remove(datasource)
        }
    }

    fun updateDatasource(datasource: Layer) {
        viewModelScope.launch {
            repository.update(datasource)
        }
    }

}




class LayerManagerViewModelFactory(
    private val repository: LayerRepository,
    private var dltbRepository: DLTBRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LayerManagerViewModel(repository, dltbRepository) as T
    }
}