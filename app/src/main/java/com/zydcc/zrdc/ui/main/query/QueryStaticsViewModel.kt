package com.zydcc.zrdc.ui.main.query

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esri.arcgisruntime.data.Feature
import com.esri.arcgisruntime.data.QueryParameters
import com.esri.arcgisruntime.data.ShapefileFeatureTable
import com.esri.arcgisruntime.geometry.SpatialReference
import com.zydcc.zrdc.core.ext.postNext
import com.zydcc.zrdc.core.ext.setNext
import com.zydcc.zrdc.db.AppDatabase
import com.zydcc.zrdc.entity.bean.IField
import com.zydcc.zrdc.entity.dic.Dltb
import com.zydcc.zrdc.entity.dic.Layer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/26:12:56 PM
 * ========================================
 */
class QueryStaticsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataBase = AppDatabase.getInstance(application)
    val dltbDao = dataBase.dltbDao()

    val shpDatasourceList: LiveData<List<Layer>> =
        dataBase.layerDao().getShpDatasourceList()
    private var _iFieldList = MutableLiveData<List<IField>>()
    var iFieldList: LiveData<List<IField>> = _iFieldList

    val mResultList = MutableLiveData<MutableList<Feature>>(
        mutableListOf<Feature>()
    )


    fun whenLayerSelected(currentLayer: Layer, dltbList: List<Dltb>) {

        val shapeFiFeatureTable = ShapefileFeatureTable(currentLayer.layerUrl)
        shapeFiFeatureTable.loadAsync()
        shapeFiFeatureTable.addDoneLoadingListener {

            val fields = shapeFiFeatureTable.fields
            val layerFieldList = mutableListOf<IField>()
            for (index in 0 until fields.size) {
                val field = fields[index]
                val name = field.name.toUpperCase(Locale.CHINA)
                var dltb: Dltb? = null
                if (name != "FID" && name != "OBJECTID" && name != "SHAPE_LENG" && name != "SHAPE_AREA") {
                    for (item in dltbList) {
                        if (name == item.zddm) {
                            dltb = item
                            break
                        }
                    }
                }

                val iField = IField(
                    checked = 0,
                    dltb = dltb,
                    field = field
                )
                layerFieldList.add(iField)
            }
            _iFieldList.value = layerFieldList
        }
    }



    fun getSearchResult(shpPath: String) {
        val shapefileFeatureTable = ShapefileFeatureTable(shpPath)
        val params = QueryParameters()
        params.isReturnGeometry = true
        params.outSpatialReference = SpatialReference.create(4326)

        GlobalScope.launch {
            val listenableFuture = shapefileFeatureTable.queryFeaturesAsync(params)
            listenableFuture.addDoneListener {
                val data = mutableListOf<Feature>()
                val featureQueryResult = listenableFuture.get()
                val iterator = featureQueryResult.iterator()
                while (iterator.hasNext()) {
                    data.add(iterator.next())
                }
                mResultList.postNext { last ->
                    last.clear()
                    last.addAll(data)
//                    job!!.cancel()
                    return@postNext last
                }
            }
        }


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

    var operateList = arrayListOf(
        "大于", "小于", "等于", "大于等于", "小于等于", "不等于", "或", "且", "包含", "LIKE"
    )


}