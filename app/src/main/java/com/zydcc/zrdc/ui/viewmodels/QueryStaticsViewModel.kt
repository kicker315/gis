package com.zydcc.zrdc.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zydcc.zrdc.db.table.Layer
import com.zydcc.zrdc.db.table.LayerRepository
import com.zydcc.zrdc.model.bean.QueryData

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/26:12:56 PM
 * ========================================
 */
class QueryStaticsViewModel(  repository: LayerRepository) : ViewModel() {

    var queryData = QueryData()

    val shpDatasourceList: LiveData<List<Layer>> =
        repository.getShpDatasourceList()


    var operateMap = hashMapOf<String, String>().also {
        it["大于"] = " > "
        it["小于"] = " < "
        it["等于"] = " = "
        it["大于等于"] = " >= "
        it["小于等于"] = " <= "
        it["不等于"] = " <> "
        it["或"] = " < "
        it["且"] = " < "
        it["包含"] = " < "
        it["LIKE"] = " LIKE "
    }

}

class QueryStaticsViewModelFactory(private val repository: LayerRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QueryStaticsViewModel(repository) as T
    }
}