package com.zydcc.zrdc.ui.main.query.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.dic.Layer

/**
 * =======================================
 * 图层选择适配器
 * Create by ningsikai 2020/6/17:5:15 PM
 * ========================================
 */
class QueryLayerSelectAdapter : BaseQuickAdapter<Layer, BaseViewHolder>(R.layout.item_layer_select_list, null) {

    override fun convert(holder: BaseViewHolder, item: Layer) {
        holder.setText(R.id.tv_filename, item.layerName)
            .setGone(R.id.checkbox, true)
    }

}