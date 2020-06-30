package com.zydcc.zrdc.ui.viewmodels

import androidx.lifecycle.*
<<<<<<< HEAD
import com.blankj.utilcode.util.LogUtils
import com.zydcc.zrdc.base.App
import com.zydcc.zrdc.greendao.LayerDao
import com.zydcc.zrdc.greendao.ProjectDao
import com.zydcc.zrdc.model.bean.Layer
=======
import com.zydcc.zrdc.db.table.DLTB
import com.zydcc.zrdc.db.repository.DLTBRepository
import com.zydcc.zrdc.db.table.Layer
import com.zydcc.zrdc.db.table.LayerRepository
>>>>>>> parent of 35bc049... 数据库框架变更
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

<<<<<<< HEAD
    val tpkDatasourceList = MutableLiveData<List<Layer>>()


    init {
        update()
    }

    private fun update() {
        shpDatasourceList.value = App.mDaoSession.layerDao.queryBuilder().where(LayerDao.Properties.IsBaseMap.eq("1")).list()
        tpkDatasourceList.value = App.mDaoSession.layerDao.queryBuilder().where(LayerDao.Properties.IsBaseMap.eq("0")).list()
    }
=======
    val dltbList: LiveData<List<DLTB>> =
        dltbRepository.getDLTBList()
>>>>>>> parent of 35bc049... 数据库框架变更


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