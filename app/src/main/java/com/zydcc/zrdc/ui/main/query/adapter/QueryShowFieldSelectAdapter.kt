package com.zydcc.zrdc.ui.main.query.adapter


import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.bean.IField

/**
 * =======================================
 * 属性值多选适配器
 * Create by ningsikai 2020/7/3:9:03 AM
 * ========================================
 */
class QueryShowFieldSelectAdapter : BaseQuickAdapter<IField, BaseViewHolder>(R.layout.item_layer_select_list, null) {

    override fun convert(holder: BaseViewHolder, item: IField) {
        holder.setText(R.id.tv_filename, if (item.dltb != null) {item.dltb!!.zdmc + "(${item.dltb!!.zddm})" } else item.field.name)
        val checkbox = holder.getView<CheckBox>(R.id.checkbox)
        checkbox.isChecked = item.checked != 0

    }
}
