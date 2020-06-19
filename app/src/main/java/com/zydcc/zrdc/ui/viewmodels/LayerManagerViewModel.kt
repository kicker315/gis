package com.zydcc.zrdc.ui.viewmodels

import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.zydcc.zrdc.base.App
import com.zydcc.zrdc.greendao.LayerDao
import com.zydcc.zrdc.greendao.ProjectDao
import com.zydcc.zrdc.model.bean.Layer
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * =======================================
 * 图层管理
 * Create by ningsikai 2020/5/25:10:50 AM
 * ========================================
 */
class LayerManagerViewModel(
): ViewModel() {

    var shpDatasourceList = MutableLiveData<List<Layer>>()



    val tpkDatasourceList = MutableLiveData<List<Layer>>()


    init {
        update()
    }

    private fun update() {
        shpDatasourceList.value = App.mDaoSession.layerDao.queryBuilder().where(LayerDao.Properties.IsBaseMap.eq("1")).list()
        tpkDatasourceList.value = App.mDaoSession.layerDao.queryBuilder().where(LayerDao.Properties.IsBaseMap.eq("0")).list()
    }


    // 添加数据源
    fun addDatasource(datasource: Layer) {
        viewModelScope.launch {
            App.mDaoSession.layerDao.insert(datasource)
            update()
        }
    }

    // 删除数据源
    fun deleteDatasource(datasource: Layer) {
        viewModelScope.launch {
            App.mDaoSession.layerDao.delete(datasource)
            update()
        }
    }

    fun updateDatasource(datasource: Layer) {
        viewModelScope.launch {
            App.mDaoSession.layerDao.update(datasource)
            update()
        }
    }

}




class LayerManagerViewModelFactory(
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LayerManagerViewModel() as T
    }
}