package com.zydcc.zrdc.ui.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.databinding.ItemFeatureAttrFieldBinding
import com.zydcc.zrdc.model.bean.IField

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/16:3:37 PM
 * ========================================
 */
class FeatureAttrFieldAdapter : BaseQuickAdapter<IField, BaseDataBindingHolder<ItemFeatureAttrFieldBinding>>(
    R.layout.item_feature_attr_field, null) {

    override fun convert(holder: BaseDataBindingHolder<ItemFeatureAttrFieldBinding>, item: IField) {
        val binding = holder.dataBinding ?: return
        binding.entity = item
        binding.executePendingBindings()
    }

}
