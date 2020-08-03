package com.zydcc.zrdc.ui.main.query.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.esri.arcgisruntime.data.Feature
import com.esri.arcgisruntime.data.Field
import com.zydcc.zrdc.R

/**
 * =======================================
 * 查询结果
 * Create by ningsikai 2020/7/10:4:08 PM
 * ========================================
 */
class QueryResultAdapter(
    private var showFieldList: List<Field>
) : BaseQuickAdapter<Feature, BaseViewHolder>(R.layout.item_query_result, null), LoadMoreModule {

    private var key1 = ""
    private var key2 = ""
    private var key3 = ""
    private var key4 = ""
    private var key5 = ""
    private var key6 = ""
    private var map = hashMapOf<Int, Boolean>()
    init {
        if (showFieldList.isNotEmpty()) {
            key1 = showFieldList[0].name
            if (showFieldList.size > 1) {
                key2 = showFieldList[1].name
            }
            if (showFieldList.size > 2) {
                key3 = showFieldList[2].name
            }
            if (showFieldList.size > 3) {
                key4 = showFieldList[3].name
            }
            if (showFieldList.size > 4) {
                key5 = showFieldList[4].name
            }
            if (showFieldList.size > 5) {
                key6 = showFieldList[5].name
            }
        }
    }

    override fun convert(holder: BaseViewHolder, item: Feature) {
        if (showFieldList.isNotEmpty()) {
            holder.setText(R.id.tv_attr1, item.attributes[key1].toString())
                .setVisible(R.id.tv_attr1, true)
        }
        if (showFieldList.size > 1) {
            holder.setText(R.id.tv_attr2, item.attributes[key2].toString())
                .setVisible(R.id.tv_attr2, true)
        }
        if (showFieldList.size > 2) {
            holder.setText(R.id.tv_attr3, item.attributes[key3].toString())
                .setVisible(R.id.tv_attr3, true)
        }
        if (showFieldList.size > 3) {
            holder.setText(R.id.tv_attr4, item.attributes[key4].toString())
                .setVisible(R.id.tv_attr4, true)
        }
        if (showFieldList.size > 4) {
            holder.setText(R.id.tv_attr5, item.attributes[key5].toString())
                .setVisible(R.id.tv_attr5, true)
        }
        if (showFieldList.size > 5) {
            holder.setText(R.id.tv_attr6, item.attributes[key6].toString())
                .setVisible(R.id.tv_attr6,true)
        }

        val checkBox = holder.getView<CheckBox>(R.id.checkbox)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            map[holder.adapterPosition] = isChecked
            if (isChecked) {
                updateMapOnChecked.invoke(holder.adapterPosition)
            } else {
                updateMapOnUnChecked.invoke(holder.adapterPosition)
            }
        }
    }

    fun setMap(map: HashMap<Int, Boolean>) {
        this.map = map
        notifyDataSetChanged()
    }

    var updateMapOnChecked:(Int) -> Unit = {}
    var updateMapOnUnChecked: (Int) -> Unit = {}


}