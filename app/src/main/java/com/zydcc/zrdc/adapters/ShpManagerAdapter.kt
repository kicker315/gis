package com.zydcc.zrdc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zydcc.zrdc.data.Datasource
import com.zydcc.zrdc.databinding.ItemLayerManagerShpBinding

/**
 * =======================================
 * Shp Geodatabase 共用适配器
 * Create by ningsikai 2020/5/26:8:33 AM
 * ========================================
 */
class ShpManagerAdapter : ListAdapter<Datasource, RecyclerView.ViewHolder>(ShpDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemLayerManagerShpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tpk = getItem(position)
        (holder as ViewHolder).bind(tpk)
    }

    inner class ViewHolder(
        private val binding: ItemLayerManagerShpBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setAttrListener {
                attrListener.invoke(binding.entity!!)
            }

            binding.setRemoveListener {
                removeListener.invoke(binding.entity!!)
            }
        }

        fun bind(item: Datasource) {
            binding.apply {
                entity = item
                executePendingBindings()
            }
        }
    }

    var removeListener: (Datasource) -> Unit = {}
    var attrListener: (Datasource) -> Unit = {}

}

private class ShpDiffCallback: DiffUtil.ItemCallback<Datasource>() {

    override fun areItemsTheSame(oldItem: Datasource, newItem: Datasource): Boolean {
        return oldItem.path == newItem.path
    }

    override fun areContentsTheSame(oldItem: Datasource, newItem: Datasource): Boolean {
        return oldItem == newItem
    }

}