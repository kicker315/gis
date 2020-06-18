package com.zydcc.zrdc.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zydcc.zrdc.databinding.ItemLayerManagerTpkBinding
import com.zydcc.zrdc.model.bean.Layer

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/26:8:33 AM
 * ========================================
 */
class TpkManagerAdapter : ListAdapter<Layer, RecyclerView.ViewHolder>(TpkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemLayerManagerTpkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tpk = getItem(position)
        (holder as ViewHolder).bind(tpk)
    }

    inner class ViewHolder(
        private val binding: ItemLayerManagerTpkBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setRemoveListener {
                removeListener.invoke(binding.entity!!)
            }

            binding.setZoomListener {
                zoomListener.invoke(binding.entity!!)
            }

            binding.setThumbnailListener {
                thumbnailListener.invoke(binding.entity!!)
            }
        }

        fun bind(item: Layer) {
            binding.apply {
                entity = item
                executePendingBindings()
            }
        }
    }

    var removeListener: (Layer) -> Unit = {}
    var zoomListener: (Layer) -> Unit = {}
    var thumbnailListener: (Layer) -> Unit = {}


}

private class TpkDiffCallback: DiffUtil.ItemCallback<Layer>() {

    override fun areItemsTheSame(oldItem: Layer, newItem: Layer): Boolean {
        return oldItem.layerIndex == newItem.layerIndex
    }

    override fun areContentsTheSame(oldItem: Layer, newItem: Layer): Boolean {
        return oldItem == newItem
    }

}