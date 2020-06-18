package com.zydcc.zrdc.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zydcc.zrdc.base.App
import com.zydcc.zrdc.greendao.LayerDao
import com.zydcc.zrdc.model.bean.Layer
import com.zydcc.zrdc.model.bean.QueryData

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/26:12:56 PM
 * ========================================
 */
class QueryStaticsViewModel() : ViewModel() {

    var queryData = QueryData()

    var shpDatasourceList = MutableLiveData<List<Layer>>()

    init {
        shpDatasourceList.value = App.mDaoSession.layerDao.queryBuilder().where(LayerDao.Properties.IsBaseMap.eq("1")).list()
    }


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

class QueryStaticsViewModelFactory(): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QueryStaticsViewModel() as T
    }
}