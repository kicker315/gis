package com.ningsk.zrdc.ui.main.query.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ningsk.zrdc.R
import com.ningsk.zrdc.entity.bean.IField

/**
 * =======================================
 * 属性值单选适配器
 * Create by ningsikai 2020/7/3:9:11 AM
 * ========================================
 */
class QueryFieldSelectAdapter : BaseQuickAdapter<IField, BaseViewHolder>(R.layout.item_layer_select_list, null) {

    override fun convert(holder: BaseViewHolder, item: IField) {
        holder.setText(R.id.tv_filename, if (item.dltb != null) {item.dltb!!.zdmc + "(${item.dltb!!.zddm})" } else item.field.name)
            .setGone(R.id.checkbox, true)
            .setGone(R.id.icon_file, true)
    }

}