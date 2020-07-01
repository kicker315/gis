package com.zydcc.zrdc.ui.main.layermanager

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.dic.Layer

/**
 * =======================================
 * Shp Geodatabase 共用适配器
 * Create by ningsikai 2020/5/26:8:33 AM
 * ========================================
 */

class ShpManagerAdapter : BaseQuickAdapter<Layer, BaseViewHolder>(
    R.layout.item_layer_manager_shp, null), DraggableModule {


    override fun convert(holder: BaseViewHolder, item: Layer) {
        holder.setText(R.id.tv_layer_name, item.layerName)
    }
}