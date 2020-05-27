package com.zydcc.zrdc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zydcc.zrdc.data.Datasource
import com.zydcc.zrdc.data.DatasourceRepository

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/26:12:56 PM
 * ========================================
 */
class QueryStaticsViewModel(  repository: DatasourceRepository) : ViewModel() {

    val shpDatasourceList: LiveData<List<Datasource>> =
        repository.getShpDatasourceList()

}

class QueryStaticsViewModelFactory(private val repository: DatasourceRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QueryStaticsViewModel(repository) as T
    }
}