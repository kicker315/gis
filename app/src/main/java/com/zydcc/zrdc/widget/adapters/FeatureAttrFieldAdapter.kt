package com.zydcc.zrdc.widget.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.bean.IField

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/16:3:37 PM
 * ========================================
 */
class FeatureAttrFieldAdapter : BaseQuickAdapter<IField, BaseViewHolder>(
    R.layout.item_feature_attr_field, null) {

    override fun convert(holder: BaseViewHolder, item: IField) {
        holder.setText(R.id.tv_attr_field_name, item.field.name)
            .setText(R.id.tv_attr_field_alias, if (item.dltb != null) item.dltb!!.zdmc else "-")
            .setText(R.id.tv_attr_field_type, item.field.fieldType.toString())
    }


}
