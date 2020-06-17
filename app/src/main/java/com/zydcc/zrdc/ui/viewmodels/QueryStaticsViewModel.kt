package com.zydcc.zrdc.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zydcc.zrdc.data.Layer
import com.zydcc.zrdc.data.LayerRepository

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/26:12:56 PM
 * ========================================
 */
class QueryStaticsViewModel(  repository: LayerRepository) : ViewModel() {

    val shpDatasourceList: LiveData<List<Layer>> =
        repository.getShpDatasourceList()

}

class QueryStaticsViewModelFactory(private val repository: LayerRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QueryStaticsViewModel(repository) as T
    }
}