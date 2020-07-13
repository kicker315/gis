package com.zydcc.zrdc.ui.main.query.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.bean.DMItem

/**
 * =======================================
 * 操作符适配器
 * Create by ningsikai 2020/7/3:9:16 AM
 * ========================================
 */
class QueryOperationAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_layer_select_list, null) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_filename, item)
            .setGone(R.id.icon_file, true)
            .setGone(R.id.checkbox, true)
    }


}