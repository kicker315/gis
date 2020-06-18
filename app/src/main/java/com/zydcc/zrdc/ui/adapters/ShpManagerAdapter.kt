package com.zydcc.zrdc.ui.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.databinding.ItemLayerManagerShpBinding
import com.zydcc.zrdc.model.bean.Layer

/**
 * =======================================
 * Shp Geodatabase 共用适配器
 * Create by ningsikai 2020/5/26:8:33 AM
 * ========================================
 */

class ShpManagerAdapter : BaseQuickAdapter<Layer, BaseDataBindingHolder<ItemLayerManagerShpBinding>>(
    R.layout.item_layer_manager_shp), DraggableModule {


    override fun convert(holder: BaseDataBindingHolder<ItemLayerManagerShpBinding>, item: Layer) {
        val binding = holder.dataBinding
        binding?.setAttrListener {
            attrListener.invoke(binding.entity!!)
        }

        binding?.setRemoveListener {
            removeListener.invoke(binding.entity!!)
        }

        binding?.apply {
            entity = item
            executePendingBindings()
        }
    }


    var removeListener: (Layer) -> Unit = {}
    var attrListener: (Layer) -> Unit = {}

}