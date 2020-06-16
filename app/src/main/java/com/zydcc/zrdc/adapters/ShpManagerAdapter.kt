package com.zydcc.zrdc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.data.Layer
import com.zydcc.zrdc.databinding.ItemLayerManagerShpBinding

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