package com.zydcc.zrdc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zydcc.zrdc.data.DLTB
import com.zydcc.zrdc.databinding.ItemDltbListBinding


/**
 * =======================================
 * 地类图斑适配器
 * Create by ningsikai 2020/5/18:4:57 PM
 * ========================================
 */

class DLTBAdapter: ListAdapter<DLTB, RecyclerView.ViewHolder>(DLTBDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DLTBViewHolder(
            ItemDltbListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dltb = getItem(position)
        (holder as DLTBViewHolder).bind(dltb)
    }

    class DLTBViewHolder(
        private val binding: ItemDltbListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DLTB) {
            binding.apply {
                dltb = item
                executePendingBindings()
            }
        }
    }

}

private class DLTBDiffCallback: DiffUtil.ItemCallback<DLTB>() {

    override fun areItemsTheSame(oldItem: DLTB, newItem: DLTB): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DLTB, newItem: DLTB): Boolean {
        return oldItem == newItem
    }

}