package com.zydcc.zrdc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zydcc.zrdc.databinding.ItemFileListBinding
import com.zydcc.zrdc.model.bean.FileItem

/**
 * =======================================
 * 文件列表
 * Create by ningsikai 2020/5/25:11:58 AM
 * ========================================
 */
class FileListAdapter: ListAdapter<FileItem, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DLTBViewHolder(
            ItemFileListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dltb = getItem(position)
        (holder as DLTBViewHolder).bind(dltb)
    }

    class DLTBViewHolder(
        private val binding: ItemFileListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FileItem) {
            binding.apply {
                entity = item
                executePendingBindings()
            }
        }
    }

}

private class DiffCallback: DiffUtil.ItemCallback<FileItem>() {

    override fun areItemsTheSame(oldItem: FileItem, newItem: FileItem): Boolean {
        return oldItem.path == newItem.path
    }

    override fun areContentsTheSame(oldItem: FileItem, newItem: FileItem): Boolean {
        return oldItem == newItem
    }

}