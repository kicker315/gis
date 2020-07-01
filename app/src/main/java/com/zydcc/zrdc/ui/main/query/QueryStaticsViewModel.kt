package com.zydcc.zrdc.ui.main.query

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zydcc.zrdc.db.AppDatabase
import com.zydcc.zrdc.entity.dic.Layer

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/26:12:56 PM
 * ========================================
 */
class QueryStaticsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataBase = AppDatabase.getInstance(application)

    private var _viewStateLiveData = MutableLiveData<QueryViewState>()
    val viewStateLiveData: LiveData<QueryViewState> = _viewStateLiveData

    val shpDatasourceList: LiveData<List<Layer>> =
        dataBase.layerDao().getShpDatasourceList()


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