package com.zydcc.zrdc.ui.main.layermanager


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.dic.Layer

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/26:8:33 AM
 * ========================================
 */
class TpkManagerAdapter : BaseQuickAdapter<Layer, BaseViewHolder>(R.layout.item_layer_manager_tpk, null) {

    override fun convert(holder: BaseViewHolder, item: Layer) {
        holder.setText(R.id.tv_layer_name, item.layerName)
    }


}

